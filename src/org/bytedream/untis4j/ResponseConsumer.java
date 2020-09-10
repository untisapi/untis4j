package org.bytedream.untis4j;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;
import java.io.IOException;

public interface ResponseConsumer<T extends BaseResponse> {

    T getResponse(Response response) throws IOException;

}
