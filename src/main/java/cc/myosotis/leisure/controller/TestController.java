package cc.myosotis.leisure.controller;

import cc.myosotis.leisure.model.Commodity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    /*
    上传文件案例
     */
    @PostMapping("/upload")
    public Object upload(Double price,
                         String details,
                         String label[],
                         String sortName,
                         String commodityName,
                         MultipartFile images[]) {

        Map<String, Object> map = new HashMap<>();
        map.put("commodityName", commodityName);
        map.put("sortName", sortName);
        map.put("details", details);
        map.put("images", images);
        map.put("label", label);
        map.put("price", price);
        return map;
    }

    /*
    下载文件案例
     */
    @GetMapping("/download")
    public Object download() {
        return null;
    }

}
