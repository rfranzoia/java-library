/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons.service;

import br.com.fr.commons.exception.WSException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author romeu
 */
public class WSService {

    private final static Logger logger = Logger.getLogger(WSService.class);
    
    public static RestTemplate createTemplate() throws WSException {
        return createTemplate(false);
    }
            
    public static RestTemplate createTemplate(boolean hasMultipleParameters) throws WSException {
        logger.info("createTemplate()");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            List<HttpMessageConverter<?>> list = new ArrayList<>();
            
            if (hasMultipleParameters) {
                list.add(new FormHttpMessageConverter());
                list.add(new StringHttpMessageConverter());
            }
            
            list.add(new MappingJackson2HttpMessageConverter());
            
            RestTemplate rt = new RestTemplate();
            rt.setMessageConverters(list);

            return rt;
        } catch (Exception ex) {
            logger.warn("Erro ao criar RestTemplate ou MessageConverters", ex);
            throw new WSException(ex);
        }
    }
    
    public static <T extends Serializable, S extends Serializable> S sendAndReceiveClassByURL(String url, T t, Class<S> clazz) throws WSException {
        logger.info("sendAndReceiveClassByURL(): " + url);
        try {
            RestTemplate restTemplate = createTemplate();
            S response = restTemplate.postForObject(url, t, clazz);
            return response;
        } catch (HttpClientErrorException hceex) {
            String responseBody = hceex.getResponseBodyAsString();
            String statusText = hceex.getStatusText();
            logger.error(responseBody);
            logger.error(statusText);
            logger.error("", hceex);
            throw new WSException("O servidor retorou uma mensagem padrão!", hceex);
        } catch (WSException | RestClientException ex) {
            logger.warn("Erro ao executar POST", ex);
            throw new WSException("Ocorreu um erro ao buscar o WEB-SERVICE!", ex);
        }
    }
    
    public static <T extends Serializable, S extends Serializable> S sendAndReceiveClassByURL(String url, MultiValueMap parameters, Class<S> clazz) throws WSException {
        logger.info("sendAndReceiveClassByURL(): " + url);
        try {
            RestTemplate template = createTemplate(true);
            S response = template.postForObject(url, parameters, clazz);
            return response;
        } catch (HttpClientErrorException hceex) {
            String responseBody = hceex.getResponseBodyAsString();
            String statusText = hceex.getStatusText();
            HttpStatus statusCode = hceex.getStatusCode();
            logger.error(responseBody);
            logger.error(statusCode.toString() + " - " + statusText);
            logger.error("", hceex);
            throw new WSException("O servidor retorou uma mensagem padrão!", hceex);
        } catch (WSException | RestClientException ex) {
            logger.warn("Erro ao executar POST", ex);
            throw new WSException("Ocorreu um erro ao buscar o WEB-SERVICE!", ex);
        }
    }
    
    public static <T extends Serializable, S extends Serializable> List<S> sendAndReceiveListByURL(String url, T t, Class<S[]> clazz) throws WSException {
        logger.info("sendAndReceiveListByURL()");
        try {
            RestTemplate restTemplate = createTemplate();
            S[] array = restTemplate.postForObject(url, t, clazz);
            if (array == null) {
                throw new WSException("A lista recebida é NULL!");
            }
            return Arrays.asList(array);
        } catch (HttpClientErrorException hceex) {
            String responseBody = hceex.getResponseBodyAsString();
            String statusText = hceex.getStatusText();
            logger.error(responseBody);
            logger.error(statusText);
            logger.error("", hceex);
            throw new WSException("O servidor retorou uma mensagem padrão!", hceex);
        } catch (WSException | RestClientException ex) {
            logger.warn("Erro ao executar POST", ex);
            throw new WSException("(sendAndReceiveListByURL) WEB-SERVICE call ERROR!", ex);
        }
    }
    
    public static <S extends Serializable> List<S> sendAndReceiveListByURL(String url, MultiValueMap parameters, Class<S[]> clazz) throws WSException {
        logger.info("sendAndReceiveListWithParamsByURL()");
        try {
            RestTemplate template = createTemplate(true);
            S[] array = template.postForObject(url, parameters, clazz);
            if (array == null) {
                throw new WSException("A lista recebida é NULL!");
            }
            return Arrays.asList(array);
        } catch (HttpClientErrorException hceex) {
            String responseBody = hceex.getResponseBodyAsString();
            String statusText = hceex.getStatusText();
            logger.error(responseBody);
            logger.error(statusText);
            logger.error("", hceex);
            throw new WSException("O servidor retorou uma mensagem padrão!", hceex);
        } catch (WSException | RestClientException ex) {
            logger.warn("Erro ao executar POST", ex);
            throw new WSException("Ocorreu um erro ao buscar o WEB-SERVICE!", ex);
        }
    }

    public static <T extends Serializable> T getClassFromWSByURL(String url, Class<T> type) throws WSException {
        logger.info("getClassFromWSByURL()");
        try {
            RestTemplate restTemplate = createTemplate();
            T t = restTemplate.getForObject(url, type);
            return t;
        } catch (HttpClientErrorException hceex) {
            String responseBody = hceex.getResponseBodyAsString();
            String statusText = hceex.getStatusText();
            logger.error(responseBody);
            logger.error(statusText);
            logger.error("", hceex);
            throw new WSException("O servidor retorou uma mensagem padrão!", hceex);
        } catch (WSException | RestClientException ex) {
            logger.warn("Erro ao executar POST", ex);
            throw new WSException("Ocorreu um erro ao buscar o WEB-SERVICE!", ex);
        }
    }

    public static <T extends Serializable> List<T> getListFromWSByURL(String url, Class<T[]> type) throws WSException {
        logger.info("getListFromWSByURL()");
        try {
            RestTemplate restTemplate = createTemplate();
            T[] array = restTemplate.getForObject(url, type);
            if (array == null) {
                throw new WSException("A lista recebida é NULL!");
            }
            return Arrays.asList(array);
        } catch (HttpClientErrorException hceex) {
            String responseBody = hceex.getResponseBodyAsString();
            String statusText = hceex.getStatusText();
            logger.error(responseBody);
            logger.error(statusText);
            logger.error("", hceex);
            throw new WSException("O servidor retorou uma mensagem padrão!", hceex);
        } catch (WSException | RestClientException ex) {
            logger.warn("Erro ao executar GET", ex);
            throw new WSException("Ocorreu um erro ao buscar o WEB-SERVICE!", ex);
        }
    }
    
}
