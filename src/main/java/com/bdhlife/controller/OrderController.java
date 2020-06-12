package com.bdhlife.controller;

import com.bdhlife.entity.Order;
import com.bdhlife.entity.WeChatUser;
import com.bdhlife.service.OrderService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //根据用户的openid回显用户的姓名地址手机号
    @PostMapping("/findUserByOpenId")
    public Result findUserByOpenId(String openId){
        try{
            WeChatUser user=orderService.findUserByOpenId(openId);
            return Result.build(200,"查询成功",user);
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }

    //发送验证码
    /*
    @RequestMapping("/sendsms")
    public Result sendsms(String phoneNumbers, HttpSession session, HttpServletRequest request,HttpServletResponse response){
        try {
            String random = String.valueOf(new Random().nextInt(899999) + 100000);//生成随机六位数验证码
            SendSms sendSms = new SendSms();
            String TemplateParam="{code:"+random+"}";
            sendSms.sendSms(phoneNumbers,"芝麻众创登陆验证","SMS_130785296",TemplateParam);
            session.setAttribute("yanzhengma",TemplateParam);
            session.setAttribute("phoneNumber",phoneNumbers);
            response.addCookie(new Cookie("JSSESIONID",request.getSession().getId()));
            return Result.ok();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    */

    //添加一个订单
    @PostMapping("/addOrder")
    public Result addOrder(
            Integer skuId,//对应sku表的id
            Integer spuId,//对应spu表的id
            Integer count,//数量
            String phone,//联系人手机号
            String address,//收件地址
            String realName,//联系姓名
            String openId//用户的openid
    ){
        try{
            int flag=orderService.addOrder(skuId,spuId,count,phone,address,realName,openId);
            if (flag==1){
                return Result.build(200,"添加成功");
            }
            return Result.build(500,"添加失败");
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }

    //查询订单列表
    @PostMapping("/findOrderList")
    public Result findOrderList(Integer spuId,String openId){
        try{
            List<Order>list=orderService.findOrderList(spuId,openId);
            return Result.build(200,"查询成功",list);
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }



}
