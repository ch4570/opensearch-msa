package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
@RequiredArgsConstructor
public class OpenSearchConfig {



    /*
    *  RestHighLevelClient Bean 등록
    *  -> SSL 우회를 위한 설정 존재
    *  -> OpenSearch 관련 설정 값은 Config Service 에서 가져옴(PORT : 8888)
    * */
    @Bean
    public RestHighLevelClient restHighLevelClient(final Environment env) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(env.getProperty("opensearch.username"), env.getProperty("opensearch.password")));

        // SSL 우회
        final SSLContextBuilder sslBuilder = SSLContexts.custom()
                .loadTrustMaterial(null, (x509Certificates, s) -> true);
        final SSLContext sslContext = sslBuilder.build();


        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(env.getProperty("opensearch.host"), Integer.parseInt(env.getProperty("opensearch.port")), env.getProperty("opensearch.protocol")))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                .setSSLContext(sslContext)
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE))
                        .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectionRequestTimeout(5000)
                                .setSocketTimeout(120000)));
    }
}
