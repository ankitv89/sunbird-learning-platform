package org.ekstep.assessment.handler.impl;

public class HandlerImplData {

    public static final String mcqBodyString = "<div class='mcq-vertical cheveron-helper'><div class='mcq-title'><p>f</p></div><i class='chevron down icon'></i><div class='mcq-options'><div data-simple-choice-interaction data-response-variable='responseValue' value=0 class='mcq-option'><p>f</p></div><div data-simple-choice-interaction data-response-variable='responseValue' value=1 class='mcq-option'><p>f</p></div></div></div>";
    public static final String vsaBodyString = "<figure class=\\\"image\\\"><img src=\\\"/assets/public/content/do_112835334818643968148/artifact/06_maths_book_1566813333849.jpg\\\" alt=\\\"do_112835334818643968148\\\" data-asset-variable=\\\"do_112835334818643968148\\\"></figure><figure class=\\\"image\\\"><img src=\\\"/assets/public/content/do_112800498067488768120/artifact/1_1562560799900.jpg\\\" alt=\\\"do_112800498067488768120\\\" data-asset-variable=\\\"do_112800498067488768120\\\"></figure><p>Test&nbsp;</p>";
    public static final String saBodyString = "<p>Test Image</p><figure class=\\\"image\\\"><img src=\\\"/assets/public/content/do_112905373272424448114/artifact/coloring_book_1575362948580.jpg\\\" alt=\\\"do_112905373272424448114\\\" data-asset-variable=\\\"do_112905373272424448114\\\"></figure>";
    public static final String laBodyString = "<figure class=\\\"image\\\"><img src=\\\"/assets/public/content/do_112835334818643968148/artifact/06_maths_book_1566813333849.jpg\\\" alt=\\\"do_112835334818643968148\\\" data-asset-variable=\\\"do_112835334818643968148\\\"></figure>";

    public static final String mcqAnswerMap = "{\"responseDeclaration\": {\n" +
            "                \"responseValue\": {\n" +
            "                    \"cardinality\": \"single\",\n" +
            "                    \"type\": \"integer\",\n" +
            "                    \"correct_response\": {\n" +
            "                        \"value\": \"2\"\n" +
            "                    }\n" +
            "                }\n" +
            "            }}";
    public static final String mcqAnswerMapMalFormed = "{\"responseDeclaration\": {\n" +
            "                \"responseValues\": {\n" +
            "                    \"cardinality\": \"single\",\n" +
            "                    \"type\": \"integer\",\n" +
            "                    \"correct_response\": {\n" +
            "                        \"value\": \"2\"\n" +
            "                    }\n" +
            "                }\n" +
            "            }}";
    public static final String vsaAnswerString = "{\"responseDeclaration\": {\n" +
            "                \"responseValue\": {\n" +
            "                    \"cardinality\": \"single\",\n" +
            "                    \"type\": \"string\",\n" +
            "                    \"correct_response\": {\n" +
            "                        \"value\": \"<p>ADSFASDF</p>\"\n" +
            "                    }\n" +
            "                }\n" +
            "            }}";
    public static final String saAnswerString = "{\"responseDeclaration\": {\n" +
            "                \"responseValue\": {\n" +
            "                    \"cardinality\": \"single\",\n" +
            "                    \"type\": \"string\",\n" +
            "                    \"correct_response\": {\n" +
            "                        \"value\": \"<p>ADSFASDFdoijadi adj koapkd apkd apodkao dao</p>\"\n" +
            "                    }\n" +
            "                }\n" +
            "            }}";
    public static final String laAnswerString = "{\"responseDeclaration\": {\n" +
            "                \"responseValue\": {\n" +
            "                    \"cardinality\": \"single\",\n" +
            "                    \"type\": \"string\",\n" +
            "                    \"correct_response\": {\n" +
            "                        \"value\": \"<p>ADSFASD lkajdo aFalk djajkdal ladfkak aldaldka;dalk dlakdlakldkalk dka;ldakd ;lakla ;lsakdl;slkd alk</p>\"\n" +
            "                    }\n" +
            "                }\n" +
            "            }}";
}
