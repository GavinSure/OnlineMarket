package servlet;

import Bean.Production;
import com.alibaba.fastjson.JSON;
import common.PageBean;
import common.ServerResponse;
import service.ProductionService;
import service.impl.ProductionServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/prod/*")
@MultipartConfig
//告诉服务器可以解析提交的二进制数据
public class ProdServlet extends BaseServlet{
    private static ProductionService productionService=new ProductionServiceimpl();
    private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Production> productions=productionService.findAllProduction();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer=response.getWriter();
        ServerResponse<List<Production>> result;
        if(productions.isEmpty()){
            result=ServerResponse.error();
        }else {
            result=ServerResponse.success(productions);
        }
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
    private void findByPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String prodName=request.getParameter("prodName");
        String prodDesc=request.getParameter("prodDesc");
        int Page=Integer.parseInt(request.getParameter("page"));
        int size=Integer.parseInt(request.getParameter("size"));
        Map<String,Object> paramMap=new HashMap<>(16);  //存储条件的
        if (prodName!=null&&!"".equals(prodName)){
            paramMap.put("prodName",prodName);
        }
        if (prodDesc!=null&&!"".equals(prodDesc)){
            paramMap.put("prodDesc",prodDesc);
        }
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        //向控制台写入数据
        PageBean<Production> pageBean=productionService.findByPage(Page,size,paramMap);
        writer.write(JSON.toJSONString(ServerResponse.success(pageBean)));
    }
    private void addOrUpdate(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
        //获得页面请求过来的商品信息
        String prodId=request.getParameter("prodId");
        String prodName = request.getParameter("prodName");
        String prodPrice = request.getParameter("prodPrice");
        String prodImage = request.getParameter("uploadImage");
        String prodDesc = request.getParameter("prodDesc");

        Production production = new Production();
        production.setProdName(prodName);
        production.setProdPrice(Double.parseDouble(prodPrice));// String转double
        production.setProdImg(prodImage);
        production.setProdDesc(prodDesc);
        if (prodId==null|| Objects.equals(prodId,"")){
            productionService.addProduction(production);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(ServerResponse.success()));
            writer.close();
        }else {
            production.setId(Long.parseLong(prodId));
            productionService.updateProduction(production);
        }
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(ServerResponse.success()));
        writer.close();
    }
    private void upload(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //1.获得上传文件数据
        Part part=request.getPart("uploadImage");//文件数据都在part对象中
        //2.指定服务器内部的文件路径
        String directory=request.getServletContext().getRealPath("img/prod/");
        String curDateStr= LocalDate.now().toString();
        //父级目录不存在
        File dir=new File(directory,curDateStr);
        if (!dir.exists()){
            dir.mkdirs();   //自动创建目录
        }
        //获得文件名称（唯一）和文件后缀
        String fileName= UUID.randomUUID().toString().replaceAll("-","");
        //获得上传文件后缀（扩展名） a.jpg
        String sourceFileName=part.getSubmittedFileName();
        String extension=sourceFileName.substring(sourceFileName.lastIndexOf("."));

        String targetFilePath=directory+curDateStr+"/"+fileName+extension;
        part.write(targetFilePath);  //IO
        PrintWriter writer=response.getWriter();
        writer.write(JSON.toJSONString(ServerResponse.success("img/prod/" + curDateStr + "/" + fileName + extension)));
        writer.close();
    }
    private void findOne(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        String prodId=request.getParameter("prodId");
        Production production=productionService.findById(prodId);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(ServerResponse.success(production)));
    }

    private void delete(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        String[] idArray=request.getParameterValues("ids[]");
        for (String id:idArray){
            productionService.deleteById(Long.parseLong(id));

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer=response.getWriter();
            writer.write(JSON.toJSONString(ServerResponse.success()));
        }
    }
    private void download(HttpServletRequest request,HttpServletResponse response) throws IOException {
        final String filePath=request.getParameter("filePath");
        System.out.println(filePath);
        final String realPath=request.getServletContext().getRealPath("/");
        String serverFilePath=realPath+filePath;

        //相应内容是二进制的文件
        response.setContentType("application/octet-stream;charset=utf-8");
        //告诉浏览器下载的是什么文件
        String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
        System.out.println(fileName);
        response.setHeader("Content-Disposition", "attachment; filename=" + URLDecoder.decode(fileName, "UTF-8"));
        BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(serverFilePath));
        ServletOutputStream outputStream=response.getOutputStream();

        //循环读写 读到文件末尾 -1
        byte[] bytes=new byte[1024];
        int len;
        while ((len=inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
        }
        outputStream.close();
        inputStream.close();
    }
}
