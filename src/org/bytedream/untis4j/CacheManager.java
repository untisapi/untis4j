package org.bytedream.untis4j;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage cache and minimize server requests and speed up the API
 *
 * @version 1.0
 * @since 1.1
 */
public class CacheManager {

    private final HashMap<UntisUtils.Method, BaseResponse> cachedInformation = new HashMap<>();

    /**
     * Adds content to the cache
     *
     * @param method method of the object to cache
     * @param content content of the cache
     *
     * @since 1.1
     */
    public <T extends BaseResponse> void add(UntisUtils.Method method, T content) {
        this.cachedInformation.put(method, content);
    }

    /**
     * Checks if a method is cached
     *
     * @param method method of the cached object you want to check
     * @return if the cache contains the given method
     *
     * @since 1.1
     */
    public boolean contains(UntisUtils.Method method) {
        return cachedInformation.containsKey(method);
    }

    /**
     * Gets content from the cache
     *
     * @param method method of the object in cache
     *
     * @since 1.1
     */
    public <T extends BaseResponse> T get(UntisUtils.Method method) {
        return (T) cachedInformation.get(method);
    }

    /**
     * Checks if the given method is in cache and depending on the result it returns the cached object or a the response of a new request
     *
     * @see CacheManager#getOrRequest(UntisUtils.Method, RequestManager, Map, ResponseConsumer)
     *
     * @since 1.1
     */
    public <T extends BaseResponse> T getOrRequest(UntisUtils.Method method, RequestManager requestManager, ResponseConsumer<? extends T> action) throws IOException {
        return getOrRequest(method, requestManager, new HashMap<>(), action);
    }

    /**
     * Checks if the given method is in cache and depending on the result it returns the cached object or a the response of a new request
     *
     * @param requestManager request manager through which the POST requests are sent
     * @param method the POST method
     * @param params params you want to send with the request
     * @param action lambda expression that gets called if the {@code method} is not in the cache manager
     * @return the response in a {@link ResponseList}
     * @throws IOException if an IO Exception occurs
     *
     * @since 1.1
     */
    public <T extends BaseResponse> T getOrRequest(UntisUtils.Method method, RequestManager requestManager, Map<String, ?> params, ResponseConsumer<? extends T> action) throws IOException {
        if (cachedInformation.containsKey(method)) {
            return (T) cachedInformation.get(method);
        } else {
            T response = action.getResponse(requestManager.POST(method.getMethod(), params));
            this.add(method, response);
            return response;
        }
    }

    /**
     * Removes a response from the cache if the given condition is true
     *
     * @param condition condition you want to check
     * @param methodToRemove response to remove
     *
     * @since 1.1
     */
    public void removeIf(boolean condition, UntisUtils.Method methodToRemove) {
        if (condition) {
            cachedInformation.remove(methodToRemove);
        }
    }

    /**
     * Updates cache content
     *
     * @param method method of the object in cache
     * @param content content of the new cache
     *
     * @since 1.1
     */
    public <T extends BaseResponse> void update(UntisUtils.Method method, T content) {
        cachedInformation.replace(method, content);
    }

    /**
     * Updates cache content with a lambda expression
     *
     * @see CacheManager#update(UntisUtils.Method, RequestManager, Map, ResponseConsumer)
     *
     * @since 1.1
     */
    public <T extends BaseResponse> void update(UntisUtils.Method method, RequestManager requestManager, ResponseConsumer<? extends T> action) throws IOException {
        cachedInformation.replace(method, action.getResponse(requestManager.POST(method.getMethod())));
    }

    /**
     * Updates cache content with a lambda expression
     *
     * @param requestManager request manager through which the POST requests are sent
     * @param method the POST method
     * @param params params you want to send with the request
     * @param action lambda expression that gets returns the new content
     * @throws IOException if an IO Exception occurs
     *
     * @since 1.1
     */
    public void update(UntisUtils.Method method, RequestManager requestManager, Map<String, ?> params, ResponseConsumer<? extends BaseResponse> action) throws IOException {
        cachedInformation.replace(method, action.getResponse(requestManager.POST(method.getMethod(), params)));
    }

}
