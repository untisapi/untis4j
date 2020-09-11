package org.bytedream.untis4j;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Class to manage cache and minimize server requests and speed up the API
 *
 * @version 1.0
 * @since 1.1
 */
public class CacheManager {

    private final Map<CacheKeys, BaseResponse> cachedInformation = Collections.synchronizedMap(new HashMap<>());

    /**
     * Adds content to the cache
     *
     * @param cacheKeys cache keys of the object to cache
     * @param content   content of the cache
     * @since 1.1
     */
    public <T extends BaseResponse> void add(CacheKeys cacheKeys, T content) {
        this.cachedInformation.put(cacheKeys, content);
    }

    /**
     * Checks if a method is cached
     *
     * @param method method of the cached object you want to check
     * @return if the cache contains the given method
     * @since 1.1
     */
    public boolean contains(UntisUtils.Method method) {
        return cachedInformation.containsKey(new CacheKeys(method));
    }

    /**
     * Gets content from the cache
     *
     * @param method method of the object in cache
     * @since 1.1
     */
    public <T extends BaseResponse> T get(UntisUtils.Method method) {
        return (T) cachedInformation.get(new CacheKeys(method));
    }

    /**
     * Checks if the given method is in cache and depending on the result it returns the cached object or a the response of a new request
     *
     * @see CacheManager#getOrRequest(UntisUtils.Method, RequestManager, Map, ResponseConsumer)
     * @since 1.1
     */
    public <T extends BaseResponse> T getOrRequest(UntisUtils.Method method, RequestManager requestManager, ResponseConsumer<? extends T> action) throws IOException {
        return getOrRequest(method, requestManager, new HashMap<>(), action);
    }

    /**
     * Checks if the given method is in cache and depending on the result it returns the cached object or a the response of a new request
     *
     * @param requestManager request manager through which the POST requests are sent
     * @param method         the POST method
     * @param params         params you want to send with the request
     * @param action         lambda expression that gets called if the {@code method} is not in the cache manager
     * @return the response in a {@link ResponseList}
     * @throws IOException if an IO Exception occurs
     * @since 1.1
     */
    public <T extends BaseResponse> T getOrRequest(UntisUtils.Method method, RequestManager requestManager, Map<String, ?> params, ResponseConsumer<? extends T> action) throws IOException {
        CacheKeys cacheKeys = new CacheKeys(method, params);

        if (cachedInformation.containsKey(cacheKeys)) {
            return (T) cachedInformation.get(cacheKeys);
        } else {
            T response = action.getResponse(requestManager.POST(method.getMethod(), params));
            this.add(cacheKeys, response);
            return response;
        }
    }

    /**
     * Removes a response from the cache if the given condition is true
     *
     * @see CacheManager#removeIf(boolean, UntisUtils.Method, Map)
     * @since 1.1
     */
    public void removeIf(boolean condition, UntisUtils.Method methodToRemove) {
        removeIf(condition, methodToRemove, new HashMap<>());
    }

    /**
     * Removes a response from the cache if the given condition is true
     *
     * @param condition      condition you want to check
     * @param methodToRemove response to remove
     * @param params         extra parameter for the method
     * @since 1.1
     */
    public void removeIf(boolean condition, UntisUtils.Method methodToRemove, Map<String, ?> params) {
        if (condition) {
            cachedInformation.remove(new CacheKeys(methodToRemove, params));
        }
    }

    /**
     * Updates cache content
     *
     * @see CacheManager#update(UntisUtils.Method, Map, BaseResponse)
     * @since 1.1
     */
    public <T extends BaseResponse> void update(UntisUtils.Method method, T content) {
        this.update(method, new HashMap<>(), content);
    }

    /**
     * Updates cache content
     *
     * @param method  method of the object in cache
     * @param params  extra parameter of the method
     * @param content content for the new cache
     * @since 1.1
     */
    public <T extends BaseResponse> void update(UntisUtils.Method method, Map<String, ?> params, T content) {
        cachedInformation.replace(new CacheKeys(method, params), content);
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
     */
    public void update(UntisUtils.Method method, RequestManager requestManager, Map<String, ?> params, ResponseConsumer<? extends BaseResponse> action) throws IOException {
        cachedInformation.replace(new CacheKeys(method, params), action.getResponse(requestManager.POST(method.getMethod(), params)));
    }

    /**
     * Class to simplify {@link CacheManager} cache control
     *
     * @version 1.0
     * @since 1.1
     */
    private static class CacheKeys {

        LinkedHashSet<UntisUtils.Method> keys1 = new LinkedHashSet<>();
        LinkedHashSet<Map<String, ?>> keys2 = new LinkedHashSet<>();

        /**
         * Initialize the {@link CacheKeys} class
         *
         * @see CacheKeys
         * @since 1.1
         */
        public CacheKeys(UntisUtils.Method method) {
            new CacheKeys(method, new HashMap<>());
        }

        /**
         * Initialize the {@link CacheKeys} class
         *
         * @param method method to cache
         * @param params extra params to identify the cache keys
         * @since 1.1
         */
        public CacheKeys(UntisUtils.Method method, Map<String, ?> params) {
            keys1.add(method);
            keys2.add(params);
        }

    }

}
