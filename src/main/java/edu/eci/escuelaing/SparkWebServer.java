package edu.eci.escuelaing;

import static spark.Spark.*;


public class SparkWebServer {

    public static void main(String... args) {
        port(getPort());
        get("hello", (req, res) -> "Hi Docker!");
        get("/", (req, res) -> getIndexResponse());
        get("/sin/:number", (req, res) -> {
            double number = Double.parseDouble(req.params("number"));
            return Math.sin(Math.toRadians(number));
        });
        get("/cos/:number", (req, res) -> {
            double number = Double.parseDouble(req.params("number"));
            return Math.cos(Math.toRadians(number));
        });
        get("/palindromo/:cadena", (req, res) -> {
            String cadena = req.params("cadena");
            if(isPalindrome(cadena)){
                return "Is palindrome";
            }
            return "it is not palindrome";
        });
        get("/magnitud/:x/:y", (req, res) -> {
            double x = Double.parseDouble(req.params("x"));
            double y = Double.parseDouble(req.params("y"));
            return Math.sqrt((x * x) + (y * y));
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    private static boolean isPalindrome(String s) {
        int length = s.length();
        boolean palindrome = true;
        for (int i = 0; i < length / 2; i++) {
            if (s.charAt(i) != s.charAt(length - i - 1)) {
                palindrome = false;
                break;
            }
        }
        return palindrome;
    }


    public static String getIndexResponse() {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>SparkWebApp</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            text-align: center;" +
                "            background-color: #f0f0f0;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "        }" +
                "        header {" +
                "            background-color: #007acc;" +
                "            color: white;" +
                "            padding: 20px 0;" +
                "        }" +
                "        section {" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            padding: 20px;" +
                "            background-color: white;" +
                "            border-radius: 5px;" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        form {" +
                "            margin: 20px 0;" +
                "        }" +
                "        label {" +
                "            display: block;" +
                "            margin-bottom: 5px;" +
                "        }" +
                "        input[type=\"number\"], input[type=\"text\"] {" +
                "            width: 100%;" +
                "            padding: 10px;" +
                "            margin-bottom: 10px;" +
                "            border: 1px solid #ccc;" +
                "            border-radius: 3px;" +
                "        }" +
                "        button {" +
                "            background-color: #007acc;" +
                "            color: white;" +
                "            border: none;" +
                "            padding: 10px 20px;" +
                "            cursor: pointer;" +
                "            border-radius: 3px;" +
                "        }" +
                "        div[id$=\"Result\"] {" +
                "            margin-top: 10px;" +
                "            font-weight: bold;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "<header>" +
                "    <h1>Taller Docker</h1>" +
                "</header>" +
                "<section>" +
                "    <form id=\"sinForm\">" +
                "        <label for=\"sinInput\">Calculate Sin:</label>" +
                "        <input type=\"number\" id=\"sinInput\" step=\"any\" required>" +
                "        <button type=\"button\" onclick=\"calculateSin()\">Send</button>" +
                "        <div id=\"sinResult\"></div>" +
                "    </form>" +
                "    <form id=\"cosForm\">" +
                "        <label for=\"cosInput\">Calculate Cos:</label>" +
                "        <input type=\"number\" id=\"cosInput\" step=\"any\" required>" +
                "        <button type=\"button\" onclick=\"calculateCos()\">Send</button>" +
                "        <div id=\"cosResult\"></div>" +
                "    </form>" +
                "    <form id=\"palindromeForm\">" +
                "        <label for=\"palindromeInput\">Check Palindrome:</label>" +
                "        <input type=\"text\" id=\"palindromeInput\" required>" +
                "        <button type=\"button\" onclick=\"checkPalindrome()\">Check</button>" +
                "        <div id=\"palindromeResult\"></div>" +
                "    </form>" +
                "    <form id=\"magnitudeForm\">" +
                "        <label for=\"xInput\">X:</label>" +
                "        <input type=\"number\" id=\"xInput\" step=\"any\" required>" +
                "        <label for=\"yInput\">Y:</label>" +
                "        <input type=\"number\" id=\"yInput\" step=\"any\" required>" +
                "        <button type=\"button\" onclick=\"calculateMagnitude()\">Calculate Magnitude</button>" +
                "        <div id=\"magnitudeResult\"></div>" +
                "    </form>" +
                "</section>" +
                "<script>" +
                "    function calculateSin() {" +
                "        const input = document.getElementById(\"sinInput\").value;" +
                "        fetch(`/sin/${input}`)" +
                "            .then(response => response.text())" +
                "            .then(result => {" +
                "                document.getElementById(\"sinResult\").textContent = `Sin(${input}) = ${result}`;" +
                "            });" +
                "    }" +
                "" +
                "    function calculateCos() {" +
                "        const input = document.getElementById(\"cosInput\").value;" +
                "        fetch(`/cos/${input}`)" +
                "            .then(response => response.text())" +
                "            .then(result => {" +
                "                document.getElementById(\"cosResult\").textContent = `Cos(${input}) = ${result}`;" +
                "            });" +
                "    }" +
                "" +
                "    function checkPalindrome() {" +
                "        const input = document.getElementById(\"palindromeInput\").value;" +
                "        fetch(`/palindromo/${input}`)" +
                "            .then(response => response.text())" +
                "            .then(result => {" +
                "                document.getElementById(\"palindromeResult\").textContent = result;" +
                "            });" +
                "    }" +
                "" +
                "    function calculateMagnitude() {" +
                "        const x = document.getElementById(\"xInput\").value;" +
                "        const y = document.getElementById(\"yInput\").value;" +
                "        fetch(`/magnitud/${x}/${y}`)" +
                "            .then(response => response.text())" +
                "            .then(result => {" +
                "                document.getElementById(\"magnitudeResult\").textContent = `Magnitud: ${result}`;" +
                "            });" +
                "    }" +
                "" +
                "    document.getElementById(\"sinInput\").addEventListener(\"keydown\", function(event) {" +
                "        if (event.key === \"Enter\") {" +
                "            event.preventDefault();" +
                "            calculateSin();" +
                "        }" +
                "    });" +
                "" +
                "    document.getElementById(\"cosInput\").addEventListener(\"keydown\", function(event) {" +
                "        if (event.key === \"Enter\") {" +
                "            event.preventDefault();" +
                "            calculateCos();" +
                "        }" +
                "    });" +
                "" +
                "    document.getElementById(\"palindromeInput\").addEventListener(\"keydown\", function(event) {" +
                "        if (event.key === \"Enter\") {" +
                "            event.preventDefault();" +
                "            checkPalindrome();" +
                "        }" +
                "    });" +
                "" +
                "    document.getElementById(\"xInput\").addEventListener(\"keydown\", function(event) {" +
                "        if (event.key === \"Enter\") {" +
                "            event.preventDefault();" +
                "            calculateMagnitude();" +
                "        }" +
                "    });" +
                "" +
                "    document.getElementById(\"yInput\").addEventListener(\"keydown\", function(event) {" +
                "        if (event.key === \"Enter\") {" +
                "            event.preventDefault();" +
                "            calculateMagnitude();" +
                "        }" +
                "    });" +
                "</script>"+
                "</body>" +
                "</html>";

    }

}