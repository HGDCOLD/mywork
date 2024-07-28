package com.cj.controller;

import com.cj.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Api(tags = "文件操作接口")
@RestController
@RequestMapping("/api/files")
public class fileController {


    private String uploadDir = "E:"+ File.separator + "shixi"+ File.separator + "mywork";
    @ApiOperation("文件上传")
    @PreAuthorize("hasAuthority('用户')")
    @PostMapping("/upload")
    public Result handleFileUpload(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail("上传失败，请选择文件");
        }
        try {
//            String uploadDir = "E:"+ File.separator + "shixi"+ File.separator + "mywork";
//            String uploadDir = "E:\\shixi\\mywork";
            File dest = new File(uploadDir , file.getOriginalFilename());
            String targetFileName;
            String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
            String lastExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            int icount = 1;
            System.out.println(dest.getPath() + "----------" + dest.getAbsolutePath());
            while(dest.exists() || !dest.createNewFile() ){
                targetFileName = fileName + "(" + icount + ")" + lastExt;
                ++icount;
                dest = new File(uploadDir , targetFileName);
            }
            file.transferTo(dest);
        } catch (IOException e) {
            return Result.fail("文件上传失败");
        }
        return Result.success("文件上传成功");
    }

    @ApiOperation("文件列表")
    @PreAuthorize("hasAuthority('用户')")
    @PostMapping("/filelist")
    public Result fileList() {
        File file = new File(uploadDir);
        String[] list = file.list();
//        File[] files = file.listFiles();
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for(String s : list){
            stringArrayList.add(s);
        }
        return Result.success(stringArrayList);
    }

    @ApiOperation(value = "文件下载", produces = "application/octet-stream")
    @PreAuthorize("hasAuthority('用户')")
    @GetMapping("/download/{fileName}")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        File file = new File(uploadDir, fileName);
        if(file.exists()) {
            InputStream fis = null;
            OutputStream outputStream = null;
            try {
                // 获取文件名
                String filename = file.getName();
                // 获取文件后缀名
                String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                // 清空response
                response.reset();
                // 设置response的Header
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
                //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
                // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
                response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(filename, "UTF-8"));
                // 告知浏览器文件的大小
//                response.addHeader("Content-Length", "" + file.length());
                outputStream = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");

                // 将文件写入输入流
                FileInputStream fileInputStream = new FileInputStream(file);
                fis = new BufferedInputStream(fileInputStream);

                byte[] buffer = new byte[1024];
                int len;
                while((len = fis.read(buffer)) != -1){
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                fis.close();
                outputStream.close();
            }
        }
    }

    @ApiOperation("头像上传")
    @PreAuthorize("hasAuthority('用户')")
    @PostMapping("/uploadAvatar")
    public Result handleFileUploadAvatar(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail("上传失败，请选择头像");
        }
        try {
//            String uploadDir = "E:"+ File.separator + "shixi"+ File.separator + "mywork";
//            String uploadDir = "E:\\shixi\\mywork";
            File dest = new File(uploadDir , file.getOriginalFilename());
            String targetFileName;
            String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
            String lastExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            int icount = 1;
            System.out.println(dest.getPath() + "----------" + dest.getAbsolutePath());
            while(dest.exists() || !dest.createNewFile() ){
                targetFileName = fileName + "(" + icount + ")" + lastExt;
                ++icount;
                dest = new File(uploadDir , targetFileName);
            }

            file.transferTo(dest);
        } catch (IOException e) {
            return Result.fail("头像上传失败");
        }
        return Result.success("头像上传成功");
    }

//    @PreAuthorize("hasAuthority('用户')")
//    @GetMapping("/download2")
//    public void downloadFile2(@RequestParam String fileName, HttpServletResponse response){
//        File file = new File(uploadDir, fileName);
//        if(file.exists()) {
//            try {
//                // 获取文件名
//                String filename = file.getName();
//                // 获取文件后缀名
//                String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
//
//                // 将文件写入输入流
//                FileInputStream fileInputStream = new FileInputStream(file);
//
//                InputStream fis = new BufferedInputStream(fileInputStream);
//                byte[] buffer = new byte[fis.available()];
//                fis.read(buffer);
//                fis.close();
//                // 清空response
//                response.reset();
//                // 设置response的Header
//                response.setCharacterEncoding("UTF-8");
//                response.setStatus(200);
//                //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//                //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
//                // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//                response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(filename, "UTF-8"));
//                // 告知浏览器文件的大小
//                response.addHeader("Content-Length", "" + file.length());
//                OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//                response.setContentType("application/octet-stream");
//                outputStream.write(buffer);
//
//                outputStream.flush();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }



}
