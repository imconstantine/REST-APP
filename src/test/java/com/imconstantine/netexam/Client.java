package com.imconstantine.netexam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imconstantine.netexam.dto.response.ErrorEntityDtoResponse;
import com.imconstantine.netexam.dto.response.StudentDtoResponse;
import com.imconstantine.netexam.dto.response.TeacherDtoResponse;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class Client {
    private static ObjectMapper mapper = new ObjectMapper();

    private String token;
    private RestTemplate template = new RestTemplate();

    public Object get(String url, Class<?> classResponse, String... params) {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (params.length != 0) {
            requestHeaders.add("Cookie", "JAVASESSIONID=" + params[0]);
        }
        try {
            ResponseEntity responseEntity = template.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity(null, requestHeaders),
                    String.class);
            return mapper.readValue((String) responseEntity.getBody(), classResponse);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() != HttpStatus.OK) {
                String exStr = exception.getResponseBodyAsString();
                try {
                    return mapper.readValue(exception.getResponseBodyAsString(), mapper.getTypeFactory()
                            .constructCollectionType(List.class, ErrorEntityDtoResponse.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object post(String url, Object object, Class<?> classResponse, String... params) {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (params.length != 0) {
            if (params[0] == null) requestHeaders.add("Cookie", "JAVASESSIONID=" + params[0]);
            else if (!params[0].equals("application/json")) requestHeaders.add("Cookie", "JAVASESSIONID=" + params[0]);
            else requestHeaders.add("Content-Type", params[0]);
        }
        try {
            ResponseEntity responseEntity = template.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity(object, requestHeaders),
                    String.class);
            if (responseEntity.getHeaders().get("Set-Cookie") != null)
                token = responseEntity.getHeaders().get("Set-Cookie").get(0).substring(14, 50);
            if (((String) responseEntity.getBody()).contains("\"type\"")) {
                classResponse = getUserClass(responseEntity);
            }
            return mapper.readValue((String) responseEntity.getBody(), classResponse);
        } catch (HttpClientErrorException exception) {
            String exceptionBody = exception.getResponseBodyAsString();
            if (exception.getStatusCode() != HttpStatus.OK) {
                try {
                    return mapper.readValue(exception.getResponseBodyAsString(), mapper.getTypeFactory()
                            .constructCollectionType(List.class, ErrorEntityDtoResponse.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (HttpServerErrorException exception) {
            String a = exception.getResponseBodyAsString();
            String b = "l";
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    public Object postWrongJson(String url, Object object, Class<?> classResponse, String... params) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", params[0]);
        if (params.length == 2) {
            requestHeaders.add("Cookie", "JAVASESSIONID=" + params[1]);
        }
        try {
            ResponseEntity responseEntity = template.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity(object, requestHeaders),
                    String.class);
            if (responseEntity.getHeaders().get("Set-Cookie") != null)
                token = responseEntity.getHeaders().get("Set-Cookie").get(0).substring(14, 50);
            if (((String) responseEntity.getBody()).contains("\"type\"")) {
                classResponse = getUserClass(responseEntity);
            }
            return mapper.readValue((String) responseEntity.getBody(), classResponse);
        } catch (HttpClientErrorException exception) {
            String exceptionBody = exception.getResponseBodyAsString();
            if (exception.getStatusCode() != HttpStatus.OK) {
                try {
                    return mapper.readValue(exception.getResponseBodyAsString(), mapper.getTypeFactory()
                            .constructCollectionType(List.class, ErrorEntityDtoResponse.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (HttpServerErrorException exception) {
            String a = exception.getResponseBodyAsString();
            String b = "l";
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object put(String url, Object object, Class<?> classResponse, String... params) {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (params.length != 0) {
            requestHeaders.add("Cookie", "JAVASESSIONID=" + params[0]);
        }
        try {
            ResponseEntity responseEntity = template.exchange(
                    url,
                    HttpMethod.PUT,
                    new HttpEntity(object, requestHeaders),
                    String.class);
            //token = responseEntity.getHeaders().get("Cookie").get(0).substring(14, 50);
            if (((String) responseEntity.getBody()).contains("\"type\"")) {
                classResponse = getUserClass(responseEntity);
            }
            return mapper.readValue((String) responseEntity.getBody(), classResponse);
        } catch (HttpClientErrorException exception) {
            String exceptionBody = exception.getResponseBodyAsString();
            if (exception.getStatusCode() != HttpStatus.OK) {
                try {
                    return mapper.readValue(exception.getResponseBodyAsString(), mapper.getTypeFactory()
                            .constructCollectionType(List.class, ErrorEntityDtoResponse.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object delete(String url, Class<?> classResponse, String... params) {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (params.length != 0) {
            requestHeaders.add("Cookie", "JAVASESSIONID=" + params[0]);
        }
        try {
            ResponseEntity responseEntity = template.exchange(
                    url,
                    HttpMethod.DELETE,
                    new HttpEntity(null, requestHeaders),
                    String.class);
            return mapper.readValue((String) responseEntity.getBody(), classResponse);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() != HttpStatus.OK) {
                try {
                    String exceptions = exception.getResponseBodyAsString();
                    return mapper.readValue(exception.getResponseBodyAsString(), mapper.getTypeFactory()
                            .constructCollectionType(List.class, ErrorEntityDtoResponse.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> getUserClass(ResponseEntity responseEntity) {
        return ((String) responseEntity.getBody()).contains("\"TEACHER\"") ? TeacherDtoResponse.class : StudentDtoResponse.class;
    }

    public String getToken() {
        return token;
    }

}
