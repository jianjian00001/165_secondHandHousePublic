package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;

import com.cl.entity.DingdanxinxiEntity;
import com.cl.entity.view.DingdanxinxiView;

import com.cl.service.DingdanxinxiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;

/**
 * 订单信息
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-25 10:27:10
 */
@RestController
@RequestMapping("/dingdanxinxi")
public class DingdanxinxiController {
    @Autowired
    private DingdanxinxiService dingdanxinxiService;





    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DingdanxinxiEntity dingdanxinxi, 
                @RequestParam(required = false) Double fangwujiagestart,
                @RequestParam(required = false) Double fangwujiageend,
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("maijia")) {
			dingdanxinxi.setMaijiazhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("yonghu")) {
			dingdanxinxi.setYonghuzhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<DingdanxinxiEntity> ew = new EntityWrapper<DingdanxinxiEntity>();

                if(fangwujiagestart!=null) ew.ge("fangwujiage", fangwujiagestart);
                if(fangwujiageend!=null) ew.le("fangwujiage", fangwujiageend);

		PageUtils page = dingdanxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, dingdanxinxi), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,DingdanxinxiEntity dingdanxinxi, 
                @RequestParam(required = false) Double fangwujiagestart,
                @RequestParam(required = false) Double fangwujiageend,
		HttpServletRequest request){
        EntityWrapper<DingdanxinxiEntity> ew = new EntityWrapper<DingdanxinxiEntity>();
                if(fangwujiagestart!=null) ew.ge("fangwujiage", fangwujiagestart);
                if(fangwujiageend!=null) ew.le("fangwujiage", fangwujiageend);

		PageUtils page = dingdanxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, dingdanxinxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DingdanxinxiEntity dingdanxinxi){
       	EntityWrapper<DingdanxinxiEntity> ew = new EntityWrapper<DingdanxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( dingdanxinxi, "dingdanxinxi")); 
        return R.ok().put("data", dingdanxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DingdanxinxiEntity dingdanxinxi){
        EntityWrapper< DingdanxinxiEntity> ew = new EntityWrapper< DingdanxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( dingdanxinxi, "dingdanxinxi")); 
		DingdanxinxiView dingdanxinxiView =  dingdanxinxiService.selectView(ew);
		return R.ok("查询订单信息成功").put("data", dingdanxinxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DingdanxinxiEntity dingdanxinxi = dingdanxinxiService.selectById(id);
		dingdanxinxi = dingdanxinxiService.selectView(new EntityWrapper<DingdanxinxiEntity>().eq("id", id));
        return R.ok().put("data", dingdanxinxi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DingdanxinxiEntity dingdanxinxi = dingdanxinxiService.selectById(id);
		dingdanxinxi = dingdanxinxiService.selectView(new EntityWrapper<DingdanxinxiEntity>().eq("id", id));
        return R.ok().put("data", dingdanxinxi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DingdanxinxiEntity dingdanxinxi, HttpServletRequest request){
    	dingdanxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(dingdanxinxi);

        dingdanxinxiService.insert(dingdanxinxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DingdanxinxiEntity dingdanxinxi, HttpServletRequest request){
    	dingdanxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(dingdanxinxi);

        dingdanxinxiService.insert(dingdanxinxi);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody DingdanxinxiEntity dingdanxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(dingdanxinxi);
        dingdanxinxiService.updateById(dingdanxinxi);//全部更新
        return R.ok();
    }

    
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        dingdanxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	







}
