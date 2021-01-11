package org.bytedream.untis4j;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;

import java.io.IOException;

/**
 * Consumer for request responses
 *
 * @version 1.0
 * @since 1.1
 */
public interface ResponseConsumer<T extends BaseResponse> {

    T getResponse(Response response) throws IOException;

}
