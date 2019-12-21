package com.test.sso.service;

import com.test.sso.entity.vo.ResponseCode;
import com.test.sso.entity.vo.ResponseVO;
import com.test.sso.utils.ExceptionUtil;
import com.test.sso.utils.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Desc:验证码相关
 * Author:Kevin
 * Date:2019/12/17
 **/
@Slf4j
@Service
public class CaptchaService {


    /**
     * 获取验证码
     * @param session 当前会话session
     * @return 统一返回体
     */
    public ResponseVO getKaptchaCode(HttpSession session) {

        ResponseVO responseVO = ResponseVO.createEmptyResponse();

        int width=200;
        int height=69;
        BufferedImage verifyImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        String randomText = VerifyCodeUtil.drawRandomText(width,height,verifyImg);
        session.setAttribute("verifyCode", randomText);
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(verifyImg, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String png_base64 =  encoder.encodeBuffer(outputStream.toByteArray()).trim();
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
            String oo = "data:image/jpg;base64," + png_base64;
            outputStream.close();
            responseVO.setCode(ResponseCode.OK).setResult(true).setData(oo);
        }catch (Exception e) {
            ExceptionUtil.handlerException4biz(responseVO, e);
        }
        return responseVO;
    }


    /**
     * 验证验证码
     * @param session 当前会话session
     * @param map 请求参数的封装，包含验证码
     * @return 统一返回体
     */
    public ResponseVO validateKaptchaCode(HttpSession session, Map<String,String> map) {

        int errorcount = (session.getAttribute("errorcount") == null ? 0:(Integer)session.getAttribute("errorcount"));
        String verifyCode = map.get("verifyCode");
        String verifyCode2 = session.getAttribute("verifyCode").toString();
        System.out.println("验证码为："+verifyCode2);
        if(errorcount >= 3){
            if(verifyCode == null||verifyCode.equals(""))
                return ResponseVO.createWithException(400, "请输入验证码");
            else if(!verifyCode.equalsIgnoreCase(session.getAttribute("verifyCode").toString())){
                return ResponseVO.createWithException(400,"验证码输入有误，请重新输入");
            }
        }
        return ResponseVO.createOKWithDataWithoutPageinfo(null, null);
    }
}
