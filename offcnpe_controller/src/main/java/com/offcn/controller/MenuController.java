package com.offcn.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.offcn.pojo.Menu;
import com.offcn.pojo.User;
import com.offcn.service.MenuService;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;
    /**
     * 获取功能权限
     * @return
     */
    @RequestMapping("getMenus")
    public Result getMenus(HttpSession session){
        Result result=new Result();
        try{
            //获取当前登录用户
            User user=(User)session.getAttribute("currentUser");
            //获取当前用户功能菜单列表
            List<Menu> menuList = menuService.findMenusByUid(user.getId());
            //将菜单转换为插件指定的json格式
            JSONArray jsonArray=new JSONArray();
            for(Menu menu:menuList){
                if(menu.getParentmenuid()==null){
                    //获取当前用户所有一级功能
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("path",menu.getPath());
                    jsonObject.put("title",menu.getName());
                    jsonObject.put("icon",menu.getIcon());
                    JSONArray children=new JSONArray();
                    //获取当前一级功能下的二级功能
                    for(Menu menu1:menuList){
                        if(menu1.getParentmenuid()==menu.getId()){
                            JSONObject jsonObject1=new JSONObject();
                            jsonObject1.put("path",menu1.getPath());
                            jsonObject1.put("title",menu1.getName());
                            jsonObject1.put("linkUrl",menu1.getLinkurl());
                            children.add(jsonObject1);
                        }
                    }
                    jsonObject.put("children",children);
                    jsonArray.add(jsonObject);
                }
            }
            result.setFlag(true);
            result.setData(jsonArray);
            result.setMessage("获取信息成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("获取信息失败");
        }
        return result;
    }
}

