/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: AppAction
 * Function: App的action
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午07:42:45
 *
 * @see      
 */

package com.jiae.iseer.action.home;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.jiae.iseer.entity.image.Image;
import com.jiae.iseer.service.image.ImageService;
//import com.jiae.iseer.service.user.UserService;
import com.jiae.iseer.basic.action.SimpleActionSupport;

@Controller
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HomeAction extends SimpleActionSupport {
    
    private static final long serialVersionUID = 1L;

    @Resource
    private ImageService imageService;
    
    private List<Image> randomImageList;
    private final static Integer RANDOM_NUMBER = 10;
    
    public String execute() {

        try {
            randomImageList = imageService.getRandomImageList(RANDOM_NUMBER);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public List<Image> getRandomImageList() {
        return randomImageList;
    }

    public void setRandomImageList(List<Image> randomImageList) {
        this.randomImageList = randomImageList;
    }

}