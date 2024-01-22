package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AssignmentsList
 */
public class AssignmentsList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// フィールド宣言
	private int SYSTEM_ADMINISTRATOR = 1;		// システム管理者
	private int ADMINISTRATOR = 2;				// 管理者
	private int GENERAL_USER = 3;				// 一般ユーザー

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignmentsList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		// 遷移先
		String dist = "";

		int privilege = Integer.parseInt((String)session.getAttribute("loginUserPrivilege"));
		if(privilege == SYSTEM_ADMINISTRATOR || privilege == ADMINISTRATOR) {
			dist = "/AdminAssignmentsList.jsp";
		} else if(privilege == GENERAL_USER) {
			dist = "/GeneralAssignmentsList.jsp";
		}

		// 画面遷移(フォワードを使ってJSPに表示を切り替える)
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher(dist);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
