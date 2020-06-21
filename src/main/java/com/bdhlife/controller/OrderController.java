package com.bdhlife.controller;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;
import com.bdhlife.entity.Order;
import com.bdhlife.entity.WeChatUser;
import com.bdhlife.service.GoodsService;
import com.bdhlife.service.OrderService;
import com.bdhlife.utils.MapUtil;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

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
            String orderNumber=orderService.addOrder(skuId,spuId,count,phone,address,realName,openId);
            if (orderNumber != null){
                return Result.build(200,"添加成功",orderNumber);
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
            List<Map> l= new ArrayList<Map>();
            for (Order order : list) {
                Map<String, String> map = MapUtil.convertToMap(order);
                Integer state = order.getState();
                if (1 == state ){
                    map.put("state","未支付");
                }
                if (2 == state){
                    map.put("state","已支付");
                }
                Integer spuId1 = order.getSpuId();
                List<Goods> goodsList = new ArrayList<>();
                goodsList =   goodsService.findGoodsList(spuId1);
                if(goodsList != null && goodsList.size() > 0) {
                    Goods goods = goodsList.get(0);
                    map.put("goodsName",goods.getName());
                    map.put("title",goods.getTitle());
                    map.put("description",goods.getDescription());
                }
                Integer skuId = order.getSkuId();
                List<KuCun> kuCuns = goodsService.queryKuCunList(null, null, skuId, null,false,false);
                if (kuCuns != null && kuCuns.size() > 0){
                    KuCun kuCun = kuCuns.get(0);
                    map.put("kucunName",kuCun.getName());
                    map.put("images",kuCun.getImages());
                    map.put("stock",String.valueOf(kuCun.getStock()));
                    map.put("size",kuCun.getSize());
                    map.put("color",kuCun.getColor());
                }
                l.add(map);
            }
            return Result.build(200,"查询成功",l);
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }



}
