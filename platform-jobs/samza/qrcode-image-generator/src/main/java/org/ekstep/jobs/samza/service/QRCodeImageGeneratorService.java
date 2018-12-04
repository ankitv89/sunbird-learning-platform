package org.ekstep.jobs.samza.service;

import org.apache.samza.config.Config;
import org.apache.samza.task.MessageCollector;
import org.ekstep.jobs.samza.model.QRCodeGenerationRequest;
import org.ekstep.jobs.samza.util.JSONUtils;
import org.ekstep.jobs.samza.util.JobLogger;
import org.ekstep.jobs.samza.util.QRCodeImageGeneratorParams;
import org.ekstep.jobs.samza.util.QRCodeImageGeneratorUtil;
import org.ekstep.jobs.samza.util.QRCodeCassandraConnector;
import org.ekstep.jobs.samza.util.CloudStorageUtil;
import org.ekstep.jobs.samza.util.ZipEditorUtil;
import org.ekstep.jobs.samza.service.task.JobMetrics;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class QRCodeImageGeneratorService implements ISamzaService {

	private JobLogger LOGGER = new JobLogger(QRCodeImageGeneratorService.class);

	private Config appConfig = null;

	@Override
	public void initialize(Config config) throws Exception {
		JSONUtils.loadProperties(config);
		appConfig = config;
		LOGGER.info("Service config initialized");
	}

	@Override
	public void processMessage(Map<String, Object> message, JobMetrics metrics, MessageCollector collector) throws Exception {
	    LOGGER.info("Processing request: "+message);
	    LOGGER.info("Starting message processing at "+System.currentTimeMillis());

		String eid = (String) message.get(QRCodeImageGeneratorParams.eid.name());
		if(!eid.equalsIgnoreCase(QRCodeImageGeneratorParams.BE_QR_IMAGE_GENERATOR.name())) {
            return;
		}

        List<Map<String,Object>> dialCodes = (List<Map<String,Object>>) message.get(QRCodeImageGeneratorParams.dialcodes.name());
		if(null == dialCodes || dialCodes.size()==0) {
		    return;
        }

        Map<String, Object> config = (Map<String, Object>) message.get(QRCodeImageGeneratorParams.config.name());
		String imageFormat = (String) config.get(QRCodeImageGeneratorParams.imageFormat.name());

        List<File> availableImages = new ArrayList<File>();
        List<String> dataList = new ArrayList<String>();
        List<String> textList = new ArrayList<String>();
        List<String> fileNameList = new ArrayList<String>();

        for(Map<String, Object> dialCode : dialCodes) {
            if(dialCode.containsKey(QRCodeImageGeneratorParams.location.name())) {
                try {
                    String downloadUrl = (String) dialCode.get(QRCodeImageGeneratorParams.location.name());
                    String fileName = (String) dialCode.get(QRCodeImageGeneratorParams.id.name());
                    File fileToSave = new File(fileName+"."+imageFormat);
                    CloudStorageUtil.downloadFile(downloadUrl, fileToSave);
                    availableImages.add(fileToSave);
                    continue;
                } catch(Exception e) {

                }
            }

            dataList.add((String)dialCode.get(QRCodeImageGeneratorParams.data.name()));
            textList.add((String)dialCode.get(QRCodeImageGeneratorParams.text.name()));
            fileNameList.add((String)dialCode.get(QRCodeImageGeneratorParams.id.name()));

        }

        Map<String, String> storage = (Map<String, String>) message.get(QRCodeImageGeneratorParams.storage.name());
        String container = storage.get(QRCodeImageGeneratorParams.container.name());
        String path = storage.get(QRCodeImageGeneratorParams.path.name());
        String processId = (String) message.get(QRCodeImageGeneratorParams.processId.name());

        QRCodeGenerationRequest qrGenRequest = getQRCodeGenerationRequest(config, dataList, textList, fileNameList);
        List<File> generatedImages = QRCodeImageGeneratorUtil.createQRImages(qrGenRequest, appConfig, container, path);

        availableImages.addAll(generatedImages);
        File zipFile = ZipEditorUtil.zipFiles(availableImages, processId);

        String zipDownloadUrl = CloudStorageUtil.uploadFile(appConfig, container, path, zipFile, true, false);
        QRCodeCassandraConnector.updateDownloadZIPUrl(processId, zipDownloadUrl);

        LOGGER.info("Message processed successfully at "+System.currentTimeMillis());

	}

	private QRCodeGenerationRequest getQRCodeGenerationRequest(Map<String, Object> config, List<String> dataList, List<String> textList, List<String> fileNameList) {
		QRCodeGenerationRequest qrGenRequest = new QRCodeGenerationRequest();
		qrGenRequest.setData(dataList);
		qrGenRequest.setText(textList);
		qrGenRequest.setFileName(fileNameList);
		qrGenRequest.setErrorCorrectionLevel((String) config.get(QRCodeImageGeneratorParams.errorCorrectionLevel.name()));
		qrGenRequest.setPixelsPerBlock((Integer) config.get(QRCodeImageGeneratorParams.pixelsPerBlock.name()));
		qrGenRequest.setQrCodeMargin((Integer) config.get(QRCodeImageGeneratorParams.qrCodeMargin.name()));
		qrGenRequest.setTextFontName((String) config.get(QRCodeImageGeneratorParams.textFontName.name()));
		qrGenRequest.setTextFontSize((Integer) config.get(QRCodeImageGeneratorParams.textFontSize.name()));
		qrGenRequest.setTextCharacterSpacing((Double) config.get(QRCodeImageGeneratorParams.textCharacterSpacing.name()));
		qrGenRequest.setFileFormat((String) config.get(QRCodeImageGeneratorParams.imageFormat.name()));
		qrGenRequest.setColorModel((String) config.get(QRCodeImageGeneratorParams.colourModel.name()));
		qrGenRequest.setImageBorderSize((Integer) config.get(QRCodeImageGeneratorParams.imageBorderSize.name()));
		return qrGenRequest;
	}
}
