package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserBean;
import model.UserDao;

/**
 * Servlet implementation class DeleteUser
 */
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int SYSTEM_ADMINISTRATOR = 1;		// システム管理者
	private int ADMINISTRATOR = 2;				// 管理者
	private int GENERAL_USER = 3;				// 一般ユーザー

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードを指定する
		request.setCharacterEncoding("UTF-8");

		// セッションを取得する
		HttpSession session = request.getSession(false);

		// ログインしていないかチェックする
		if(session == null || session.getAttribute("auth") == null) {
			// ログインしていない場合、ログイン画面へ遷移する
			// jspへ結果のセット
			request.setAttribute("result", -2);
			// 画面遷移(フォワードを使ってJSPに表示を切り替える)
			ServletContext app = this.getServletContext();
			RequestDispatcher dispatcher = app.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
			return;
		};

		UserDao ud = new UserDao();

		String userId = (String)request.getParameter("userId");

		ArrayList<String> err = new ArrayList<>();

		String path = this.getServletContext().getRealPath("folders");
		int num = ud.deleteDao(userId, path);
		if(num != 1) {
			err.add("指定されたユーザーは既に削除されているか存在しません。");
		}

		if(err.size() == 0) {
			request.setAttribute("deleteResult", "1");
			request.setAttribute("msg", "ユーザーの削除に成功しました。");
		} else {
			request.setAttribute("deleteResult", "-1");
			request.setAttribute("err", err);
		}

		ArrayList<UserBean> ubList = ud.selectSearchDao("", GENERAL_USER);

		request.setAttribute("ubList", ubList);

		// 画面遷移(フォワードを使ってJSPに表示を切り替える)
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/UserManagement.jsp");
		dispatcher.forward(request, response);
	}

}
