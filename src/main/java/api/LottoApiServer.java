package api;

import db.LottoDatabase;
import org.json.simple.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class LottoApiServer {

    public static void main(String args[]) {
        for(int i=1; i<1000; i++){
            getLottoInfo(i);
        }
        //getLottoInfo();
    }

    @RequestMapping(value = "")
    public static void getLottoInfo(int round)  {
        try{
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setReadTimeout(5000); // 읽기시간초과, ms
            factory.setConnectTimeout(3000); // 연결시간초과, ms
            HttpClient httpClient = HttpClientBuilder.create()
                    .setMaxConnTotal(100) // connection pool 적용
                    .setMaxConnPerRoute(5) // connection pool 적용
                    .build();
            factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
            RestTemplate restTemplate = new RestTemplate(factory);
            //String url = ""; // 예제니까 애초에 때려박음..

            String url = "https://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo=" + round;
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                Object obj = parser.parse(result.getBody());
                jsonObject = (JSONObject) obj;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            LottoDatabase database = new LottoDatabase();
            database.appendLottoInfo(jsonObject);

            System.out.println(result);
            System.out.println("json \n" + jsonObject.get("totSellamnt"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
