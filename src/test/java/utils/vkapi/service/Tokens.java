package utils.vkapi.service;

public class Tokens {
    public static String serviceToken = System.getProperty("serviceToken", "xxx");


    /*
    https://oauth.vk.com/authorize?client_id=xxx&display=page&redirect_uri=https://bot.com&scope=notify&response_type=code&v=5.92

    https://oauth.vk.com/access_token?client_id=xxx&client_secret=xxx&redirect_uri=https://bot.com&code={code}
     */

    public static String codeFlowToken = System.getProperty("codeFlowToken", "xxx");


    /*
    https://oauth.vk.com/authorize?client_id=xxx&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=friends&response_type=token&v=5.92&state=123456
     */
    public static String implicitFlowToken = System.getProperty("implicitToken", "xxx");
}
