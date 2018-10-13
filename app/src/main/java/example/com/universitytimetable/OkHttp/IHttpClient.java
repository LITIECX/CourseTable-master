package example.com.universitytimetable.OkHttp;

import java.io.File;
import java.util.Map;

public interface IHttpClient {
    void  get(IRequest request,OnResultListener listener);
    IResponse get(IRequest request);

    /**
     * json格式的post * @param request * @return
     */
    void  post(IRequest request,OnResultListener listener);
    IResponse post(IRequest request);

    /**
     * 表单类型的post * @param request * @param map * @param file * @return
     */
    void  upload_image_post(IRequest request, Map<String, Object> map, File file,OnResultListener listener);

    void delete(IRequest request,OnResultListener listener);

    void put(IRequest request,OnResultListener listener);
}
