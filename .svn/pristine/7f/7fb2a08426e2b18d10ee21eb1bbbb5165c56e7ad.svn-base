package com.bmtc.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.controller.BaseController;
import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.MD5Utils;
import com.bmtc.common.utils.R;
import com.bmtc.common.utils.ShiroUtils;
import com.bmtc.svn.domain.SvnConfDiffInfo;
import com.bmtc.svn.service.SvnConfDiffService;
import com.bmtc.system.domain.MenuDO;
import com.bmtc.system.domain.UserDO;
import com.bmtc.system.domain.UserLoginStatus;
import com.bmtc.system.service.MenuService;
import com.bmtc.system.service.UserService;

/**
 * 登录
 * 
 * @author nienannan
 *
 */
@Controller
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	MenuService menuService;
	
	@Autowired
	SvnConfDiffService svnConfDiffService;
	
	@Autowired
	UserService userService;

	@GetMapping({ "/", "" })
	String welcome(Model model) {
		logger.info("LoginController.welcome() start");
		logger.info("LoginController.welcome() end");
		// return "redirect:/blog";
		// 访问直接到登录界面，绕过blog界面
		return "redirect:/login";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		logger.info("LoginController.index() start");
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		model.addAttribute("username", getUser().getUsername());
		logger.info("LoginController.index() end");
		return "index_v1";
	}

	@Log("访问登录页")
	@GetMapping("/login")
	String login() {
		logger.info("LoginController.login() start");
		logger.info("LoginController.login() end");
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
		logger.info("LoginController.ajaxLogin() start");
		try {
			password = MD5Utils.encryptPureMD5(password);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("计算密码的MD5值错误");
			return R.error("登录异常");
		}
		
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			UserDO user = ShiroUtils.getUser();
			if (user.getStatus() == UserLoginStatus.APPROVAL_PENDING) {
				logger.info("LoginController.ajaxLogin() end");
				return R.error("账号未审批,请联系管理员");
			}
			if (user.getStatus() == UserLoginStatus.DISABLED) {
				logger.info("LoginController.ajaxLogin() end");
				return R.error("账号已被锁定,请联系管理员");
			}
			logger.info("LoginController.ajaxLogin() end");
			return R.ok();

		} catch (AuthenticationException e) {
			return R.error("用户名或密码错误");
		}
	}

	@Log("登出到登录页")
	@GetMapping("/logout")
	String logout() {
		logger.info("LoginController.logout() start");
		ShiroUtils.logout();
		logger.info("LoginController.logout() end");
		return "redirect:/login";
	}

	@Log("访问主页")
	@GetMapping("/main")
	String main(Model model) {
		logger.info("LoginController.main() start");
		Map<String, Object> params = new HashMap<String, Object>();
		// 查询svn配置文件差异信息
		List<SvnConfDiffInfo> svnConfDiffInfoList = svnConfDiffService.list(params);
		// 根据数据库生成的svn配置文件和svn服务器的配置文件是否相同
		String compareResultIsDifferent = "0";
		for(SvnConfDiffInfo svnConfDiffInfo : svnConfDiffInfoList) {
			if("不相同".equals(svnConfDiffInfo.getPasswdStatus()) || "不相同".equals(svnConfDiffInfo.getAuthzStatus())
					|| "不相同".equals(svnConfDiffInfo.getSvnserverStatus())) {
				compareResultIsDifferent = "1";
				break; // 如果存在某个仓库的某一个配置文件与svn服务器不同，则给标志位compareResultIsDifferent置1，并跳出循环
			}
		}
		model.addAttribute("compareResultIsDifferent", compareResultIsDifferent);
		// 当前登录用户角色是系统管理员
		String isAdmin = "0";
		// 根据当前登录用户id，获取当前登录用户的角色
		UserDO user = userService.get(getUserId());
		List<Long> roleIds = user.getRoleIds();
		for(Long roleId: roleIds) {
			if(roleId == 1) {
				isAdmin = "1";
				break; // 若当前用户角色中含有系统管理员角色，则给标志位isAdmin置1，并跳出循环
			}
		}
		model.addAttribute("isAdmin", isAdmin);
		logger.info("LoginController.main() end");
		return "main";
	}
	
	@Log("访问了解BMTC页面")
	@GetMapping("/bmtc")
	String bmtc() {
		logger.info("LoginController.bmtc() start");
		logger.info("LoginController.bmtc() end");
		return "bmtc";
	}

	@Log("访问403错误页")
	@GetMapping("/403")
	String error403() {
		logger.info("LoginController.error403() start");
		logger.info("LoginController.error403() end");
		return "403";
	}
	

}
