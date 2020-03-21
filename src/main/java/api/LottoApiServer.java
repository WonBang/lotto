package api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class LottoApiServer {

    public static void main(String args[]) {
        getLottoInfo();
    }

    @RequestMapping(value = "")
    public static void getLottoInfo() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000); // 읽기시간초과, ms
        factory.setConnectTimeout(3000); // 연결시간초과, ms
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100) // connection pool 적용
                .setMaxConnPerRoute(5) // connection pool 적용
                .build();
        factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
        RestTemplate restTemplate = new RestTemplate(factory);
        String url = "http://testapi.com/search?text=1234"; // 예제니까 애초에 때려박음..
        url = "https://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo=700";
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);
        Object obj = restTemplate.getForObject(url, String.class, httpEntity);
        System.out.println(obj);


    }
}
