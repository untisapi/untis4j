package org.bytedream.untis4j;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class to manage cache and minimize server requests and speed up the API
 *
 * @version 1.0
 * @since 1.1
 */
public class CacheManager {

    private final CacheInformation cachedInformation = new CacheInformation();
    private boolean threadSafe = false;

    /**
     * Adds content to the cache
     *
     * @param keys    keys of the object to cache
     * @param content content of the cache
     * @since 1.1
     */
    public <T extends BaseResponse> void add(Object[] keys, T content) {
        this.cachedInformation.put(keys, content);
    }

    /**
     * Checks if a method is cached
     *
     * @param method method of the cached object you want to check
     * @return if the cache contains the given method
     * @since 1.1
     */
    public boolean contains(UntisUtils.Method method) {
        return cachedInformation.containsKey(new Object[]{method, new HashMap<>()});
    }

    /**
     * Gets content from the cache
     *
     * @param method method of the object in cache
     * @since 1.1
     */
    public <T extends BaseResponse> T get(UntisUtils.Method method) {
        return (T) cachedInformation.get(new Object[]{method, new HashMap<>()});
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
     * @param method         the POST method
     * @param requestManager request manager through which the POST requests are sent
     * @param params         params you want to send with the request
     * @param action         lambda expression that gets called if the {@code method} is not in the cache manager
     * @return the response in a {@link ResponseList}
     * @throws IOException if an IO Exception occurs
     * @since 1.1
     */
    public <T extends BaseResponse> T getOrRequest(UntisUtils.Method method, RequestManager requestManager, Map<String, ?> params, ResponseConsumer<T> action) throws IOException {
        Object[] key = {method, params};

        Function<Object[], BaseResponse> function = (objects) -> {
            try {
                BaseResponse response = action.getResponse(requestManager.POST(method.getMethod(), params));
                this.add(key, response);
                return response;
            } catch (IOException e) {
                return null;
            }
        };

        if (threadSafe) {
            return (T) cachedInformation.computeIfAbsent(key, function);
        } else {
            return (T) cachedInformation.alternateComputeIfAbsent(key, function);
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
            cachedInformation.remove(new Object[]{methodToRemove, params});
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
        cachedInformation.replace(new Object[]{method, params}, content);
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
        cachedInformation.replace(new Object[]{method, params}, action.getResponse(requestManager.POST(method.getMethod(), params)));
    }

    /**
     * Returns if the cache manager operates thread safe or not
     *
     * @return thread save or not
     * @since 1.1
     */
    public boolean isThreadSafe() {
        return threadSafe;
    }

    /**
     * Sets if the cache manager should operate thread safe or not
     *
     * @param threadSafe set thread safe or not
     * @since 1.1
     */
    public void setThreadSafe(boolean threadSafe) {
        this.threadSafe = threadSafe;
    }

    /**
     * Class to simplify {@link CacheInformation}
     *
     * @version 1.0
     * @since 1.1
     */
    private static class CacheInformation extends HashMap<Object[], BaseResponse> {

        /**
         * Non thread save but faster version of {@link Map#computeIfAbsent(Object, Function)}
         *
         * @param key key with which the specified value is to be associated
         * @param mappingFunction the function to compute a value
         * @return the current (existing or computed) value associated with the specified key, or null if the computed value is null
         * @since 1.1
         */
        public BaseResponse alternateComputeIfAbsent(Object[] key, Function<? super Object[], ? extends BaseResponse> mappingFunction) {
            if (this.containsKey(key)) {
                return this.get(key);
            } else {
                BaseResponse response = mappingFunction.apply(key);
                this.put(key, response);
                return response;
            }
        }

        @Override
        public boolean containsKey(Object key) {
            Object[] realKey = (Object[]) key;
            return this.keySet().stream().anyMatch(k -> k[0].equals(realKey[0]) && k[1].toString().equals(realKey[1].toString()));
        }

        @Override
        public BaseResponse get(Object key) {
            Object[] realKey = (Object[]) key;
            try {
                return this.entrySet().stream().filter(baseResponseEntry -> containsKey(realKey)).findAny().orElse(null).getValue();
            } catch (NullPointerException e) {
                return null;
            }
        }
    }

}
