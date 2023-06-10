package org.bytedream.untis4j;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage cache and minimize server requests and speed up the API
 *
 * @version 1.0
 * @since 1.1
 */
@Deprecated(since = "1.3.4")
public class CacheManager {

    private final HashMap<Integer, BaseResponse> cachedInformation = new HashMap<>();

    /**
     * Checks if a method is cached
     *
     * @param method method of the cached object you want to check
     * @return if the cache contains the given method
     * @since 1.1
     * @deprecated Use the RequestManager cache
     */
    @Deprecated(since = "1.3.4")
    public boolean contains(UntisUtils.Method method) {
        return cachedInformation.containsKey(mapCode(method, new HashMap<>()));
    }

    /**
     * Checks if the given method is in cache and depending on the result it returns the cached object or a response of a new request
     *
     * @param method the request methode
     * @param action the request action
     * @param requestManager the request manager
     * @param <T> the object type which extends BaseResponse
     * @return returns the cached object
     * @see CacheManager#getOrRequest(UntisUtils.Method, RequestManager, Map, ResponseConsumer)
     * @since 1.1
     * @deprecated Use the RequestManager cache
     */
    @Deprecated(since = "1.3.4")
    public <T extends BaseResponse> T getOrRequest(UntisUtils.Method method, RequestManager requestManager, ResponseConsumer<? extends T> action) {
        return getOrRequest(method, requestManager, new HashMap<>(), action);
    }

    /**
     * Checks if the given method is in cache and depending on the result it returns the cached object or a response of a new request
     *
     * @param method         the POST method
     * @param requestManager request manager through which the POST requests are sent
     * @param params         params you want to send with the request
     * @param action         lambda expression that gets called if the {@code method} is not in the cache manager
     * @param <T>            an object which extends {@link BaseResponse}
     * @return the response in a {@link ResponseList}
     * @since 1.1
     * @deprecated Use the RequestManager cache
     */
    @Deprecated(since = "1.3.4")
    public <T extends BaseResponse> T getOrRequest(UntisUtils.Method method, RequestManager requestManager, Map<String, ?> params, ResponseConsumer<T> action) {
        int keyHashCode = mapCode(method, params);

        BaseResponse response;
        if ((response = cachedInformation.get(keyHashCode)) == null) {
            try {
                response = action.getResponse(requestManager.POST(method.getMethod(), params));
            } catch (IOException ignore) {}
            cachedInformation.put(keyHashCode, response);
        }

        return (T) response;
    }

    /**
     * Updates cache content with a lambda expression
     *
     * @param requestManager request manager through which the POST requests are sent
     * @param method         the POST method
     * @param params         params you want to send with the request
     * @param action         lambda expression that gets returns the new content
     * @throws IOException if an IO Exception occurs
     * @since 1.1
     * @deprecated Use the RequestManager cache
     */
    @Deprecated(since = "1.3.4")
    public void update(UntisUtils.Method method, RequestManager requestManager, Map<String, ?> params, ResponseConsumer<? extends BaseResponse> action) throws IOException {
        cachedInformation.replace(mapCode(method, params), action.getResponse(requestManager.POST(method.getMethod(), params)));
    }

    /**
     * Generates an individual code for every method param pair
     *
     * @param method method to generate code from
     * @param params params to generate code from
     * @return the code
     * @since 1.1
     */
    private int mapCode(UntisUtils.Method method, Map<String, ?> params) {
        return Arrays.toString(new Object[]{method, params}).hashCode();
    }
}
