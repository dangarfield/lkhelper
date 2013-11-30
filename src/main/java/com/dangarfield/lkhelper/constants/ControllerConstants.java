package com.dangarfield.lkhelper.constants;

public class ControllerConstants {

	public ControllerConstants()
	{
		// restrict instantiation
	}

	public final class Views
	{
		public Views()
		{
			// restrict instantiation
		}

		public final class Home
		{
			public Home()
			{
				// restrict instantiation
			}
			
			public final static String Home = "home/home";
		}
		
		public final class User
		{
			public User()
			{
				// restrict instantiation
			}

			public final static String UserHome = "user/userHome";
			public final static String UserEditDetails = "user/editUserDetails";
		}

		public final class Report
		{
			public Report()
			{
				// restrict instantiation
			}

			public final static String ReportHome = "report/reportHome";
			public final static String PlayerScorecard = "report/playerScorecard";
			public final static String PlayerCompare = "report/playerCompare";
			public final static String AllianceScorecard = "report/allianceScorecard";
			public final static String AllianceCompare = "report/allianceCompare";
			public final static String CastleScorecard = "report/castleScorecard";
			public final static String CastleCompare = "report/castleCompare";
		}
		
		public final class Gather
		{
			public Gather()
			{
				// restrict instantiation
			}

			public final static String GatherHome = "gather/gather";
			public final static String GatherAllCastleData = "/gather/allCastleData";
			public final static String GatherSpecificServerCastleData = "/gather/specificServerData";
			public final static String GatherAllPlayerCastleData = "/gather/allPlayerData";
			public final static String GatherSpecificPlayerCastleData = "/gather/specificPlayerData";
		}
		
		public final class Login
		{
			public Login()
			{
				// restrict instantiation
			}
			
			public final static String Login = "login/login";
			public final static String Authenticate = "login/authenticate";
			
		}

		public final class Admin
		{
			public Admin()
			{
				// restrict instantiation
			}

			public final static String AdminHome = "admin/adminHome";
			public final static String AdminEditServers = "admin/editServers";
			public final static String AdminEditUsers= "admin/editUsers";
			public final static String AdminTriggerEnsureIndexes= "admin/ensureIndexes";
			public final static String AdminCreateCSVData= "admin/csvData";
		}

		public final class Search
		{
			public Search()
			{
				// restrict instantiation
			}

			public final static String SearchResults = "search/results";
			
		}
		
		public final class Tools
		{
			public Tools()
			{
				// restrict instantiation
			}

			public final static String ToolsHome = "tools/toolsHome";
			public final static String MapCreator = "tools/mapCreator";
			public final static String BattleCalculator = "tools/battleCalculator";
			
		}

		public final class Error
		{
			public Error()
			{
				// restrict instantiation
			}

			public final static String AccessDenied403 = "error/403-AccessDenied";
			public final static String PageNotFound404 = "error/404-PageNotFound";
			
		}
		
		public final class Suggestions
		{
			public Suggestions()
			{
				// restrict instantiation
			}

			public final static String Suggestions = "suggestions/suggestions";
			
		}
	}
	
	public final class URL
	{
		public URL()
		{
			// restrict instantiation
		}
		
		public final class PathVariable
		{
			public PathVariable()
			{
				// restrict instantiation
			}
			
			public final static String ServerID = "serverId";
			public final static String PlayerID = "playerId";
			public final static String CastleID = "castleId";
			public final static String AllianceID = "allianceId";
			public final static String UserEmail = "userEmail";
			public final static String UserEmailMatchAll = "userEmail:.*";
			public final static String CompareItems = "compareItem";
			public final static String CompareItemsMatchAll = "compareItem:.*";
			public final static String LogID = "logId";
			public final static String CompareMapString = "compareMapString";
			
			public final static String SearchString = "searchString";
			public final static String SearchPage = "searchPage";
		}
		
		public final class Home
		{
			public Home()
			{
				// restrict instantiation
			}
			
			public final static String Home = "/";
			
			
		}
		
		public final class User 
		{
			public User()
			{
				// restrict instantiation
			}
			
			public final static String UserHome = "/user-home";
			public final static String UserAuthentication = "/user-home/authenticate";
			public final static String UserEditDetails = "/user-home/edit-details";
			
		}

		public final class Gather
		{
			public Gather()
			{
				// restrict instantiation
			}
			
			public final static String GatherHome = "/gather-data";
			public final static String GatherAllCastleData = "/gather-data/all-castle-data/all";
			public final static String GatherSpecificServerCastleDataBase = "/gather-data/all-castle-data/server";
			public final static String GatherSpecificServerCastleData = GatherSpecificServerCastleDataBase + "/{"+PathVariable.ServerID+"}";
			public final static String GatherAllPlayerCastleData = "/gather-data/player-castle-data/all";
			public final static String GatherSpecificPlayerCastleDataBase = "/gather-data/player-castle-data/player";
			public final static String GatherSpecificPlayerCastleData = GatherSpecificPlayerCastleDataBase + "/{"+PathVariable.UserEmailMatchAll+"}";
			public final static String GatherLogs = "/gather-data/log/{"+PathVariable.LogID+"}";
			
		}
		
		public final class Report
		{
			public Report()
			{
				// restrict instantiation
			}
			
			public final static String CompareDivider = "-";
			
			public final static String ReportHome = "/report-home";
			public final static String FixReportData = "/report-home/fix-data/{"+PathVariable.ServerID+"}";
			
			public final static String OwnPlayerReportScorecard = "/report-home/player-report/scorecard";
			public final static String PlayerReportScorecard = "/report-home/player-report/scorecard/{"+PathVariable.ServerID+"}/{"+PathVariable.PlayerID+"}";
			public final static String PlayerReportCompareBase = "/report-home/player-report/compare";
			public final static String PlayerReportCompare = "/report-home/player-report/compare/{"+PathVariable.ServerID+"}/{"+PathVariable.CompareItemsMatchAll+"}";
			
			public final static String OwnAllianceReportScorecard = "/report-home/alliance-report/scorecard";
			public final static String AllianceReportScorecard = "/report-home/alliance-report/scorecard/{"+PathVariable.ServerID+"}/{"+PathVariable.AllianceID+"}";
			public final static String AllianceReportCompareBase = "/report-home/alliance-report/compare";
			public final static String AllianceReportCompare = "/report-home/alliance-report/compare/{"+PathVariable.ServerID+"}/{"+PathVariable.CompareItemsMatchAll+"}";
			
			public final static String OwnCastleReportScorecard = "/report-home/castle-report/scorecard";
			public final static String CastleReportScorecard = "/report-home/castle-report/scorecard/{"+PathVariable.ServerID+"}/{"+PathVariable.CastleID+"}";
			public final static String CastleReportCompareBase = "/report-home/castle-report/compare";
			public final static String CastleReportCompare = "/report-home/castle-report/compare/{"+PathVariable.ServerID+"}/{"+PathVariable.CompareItemsMatchAll+"}";
			
		}
		
		public final class Login
		{
			public Login()
			{
				// restrict instantiation
			}
			
			public final static String Login = "/login";
			public final static String LoginPostURL = "/login/j_spring_security_check";
			public final static String LoginUpdatePassword = "/update-password";
			public final static String LoginFailed = "/loginfailed";
			public final static String Logout = "/logout";
			public final static String Register = "/register";
			public final static String Authenticate = "/authenticate";
			
		}

		public final class Admin
		{
			public Admin()
			{
				// restrict instantiation
				
			}

			public final static String AdminHome = "/admin-home";
			public final static String AdminEditServers = "/admin-home/edit-servers";
			public final static String AdminEditUsers = "/admin-home/edit-users";
			public final static String AdminTriggerEnsureIndexes = "/admin-home/ensure-indexes";
			public final static String AdminCreateCSVData = "/admin-home/create-csv-data";
			
		}
		
		public final class Maps
		{
			public Maps()
			{
				// restrict instantiation
				
			}
			public final static String ImageFormat = "png";
			public final static String BaseMapName = "all-castles";
			public final static String BaseMapRoot = "/maps/base/";
			public final static String AllianceMapRoot = "/maps/alliance/";
			public final static String PlayerMapRoot = "/maps/player/";
			public final static String CastleMapRoot = "/maps/castle/";
			public final static String CompareMapRoot = "/maps/compare/";
			public final static String CompareDividerItem = "_";
			public final static String CompareDividerAttribute = "-";
			public final static String CompareTypeAlliance = "a";
			public final static String CompareTypePlayer = "p";
			public final static String CompareTypeCastle = "c";
			
			public final static String BaseMap = BaseMapRoot+"{"+PathVariable.ServerID+"}/"+BaseMapName+"."+ImageFormat;
			public final static String AllianceMap = AllianceMapRoot+"{"+PathVariable.ServerID+"}/{"+PathVariable.AllianceID+"}."+ImageFormat;
			public final static String PlayerMap = PlayerMapRoot+"{"+PathVariable.ServerID+"}/{"+PathVariable.PlayerID+"}."+ImageFormat;
			public final static String CastleMap = CastleMapRoot+"{"+PathVariable.ServerID+"}/{"+PathVariable.CastleID+"}."+ImageFormat;
			public final static String CompareMap = CompareMapRoot+"{"+PathVariable.ServerID+"}/{"+PathVariable.CompareMapString+"}."+ImageFormat;
			
			
			
			
		}
		public final class Search
		{
			public Search()
			{
				// restrict instantiation
				
			}

			public final static String SearchBase = "/search";
			public final static String Search = SearchBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			
			public final static String SearchCastleBase = "/search/castle";
			public final static String SearchPlayerBase = "/search/player";
			public final static String SearchAllianceBase = "/search/alliance";
			public final static String SearchCastleParam = SearchCastleBase + "/{"+PathVariable.ServerID+"}";
			public final static String SearchPlayerParam = SearchPlayerBase + "/{"+PathVariable.ServerID+"}";
			public final static String SearchAllianceParam = SearchAllianceBase + "/{"+PathVariable.ServerID+"}";
			public final static String SearchCastle = SearchCastleBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			public final static String SearchPlayer = SearchPlayerBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			public final static String SearchAlliance = SearchAllianceBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";

			public final static String TypeaheadCastleBase = "/search/type-ahead/castle";
			public final static String TypeaheadPlayerBase = "/search/type-ahead/player";
			public final static String TypeaheadAllianceBase = "/search/type-ahead/alliance";
			public final static String TypeaheadCastle = TypeaheadCastleBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			public final static String TypeaheadPlayer = TypeaheadPlayerBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			public final static String TypeaheadAlliance = TypeaheadAllianceBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			
			public final static String CompareCastleBase = "/search/compare/castle";
			public final static String ComparePlayerBase = "/search/compare/player";
			public final static String CompareAllianceBase = "/search/compare/alliance";
			public final static String CompareCastleParam = CompareCastleBase + "/{"+PathVariable.ServerID+"}";
			public final static String ComparePlayerParam = ComparePlayerBase + "/{"+PathVariable.ServerID+"}";
			public final static String CompareAllianceParam = CompareAllianceBase + "/{"+PathVariable.ServerID+"}";
			public final static String CompareCastle = CompareCastleBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			public final static String ComparePlayer = ComparePlayerBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			public final static String CompareAlliance = CompareAllianceBase + "/{"+PathVariable.ServerID+"}/{"+PathVariable.SearchString+"}";
			
		}
		
		public final class Tools
		{
			public Tools()
			{
				// restrict instantiation
				
			}

			public final static String MapCreatorBase = "/tools/map-creator";
			public final static String MapCreatorServerOnly = MapCreatorBase+"/{"+PathVariable.ServerID+"}";
			public final static String MapCreatorMain = MapCreatorBase+"/{"+PathVariable.ServerID+"}/{"+PathVariable.CompareMapString+"}";
			public final static String BattleCalculatorBase = "/tools/battle-calculator";
			
		}

		public final class Error
		{
			public Error()
			{
				// restrict instantiation
				
			}
			
			public final static String AccessDenied403 = "/403-access-denied";
			public final static String PageNotFound404 = "/404-page-not-found";
		}
		
		public final class Suggestions
		{
			public Suggestions()
			{
				// restrict instantiation
				
			}
			
			public final static String Suggestions = "/suggestions";
		}
			
	}
	
	// Annoying, cannot access heirarchical variables with unstandard taglib
	
	public final static String URL_Home_Home = ControllerConstants.URL.Home.Home;
	
	public final static String URL_User_UserHome = ControllerConstants.URL.User.UserHome;
	public final static String URL_User_UserAuthentication = ControllerConstants.URL.User.UserAuthentication;
	public final static String URL_User_UserEditDetails = ControllerConstants.URL.User.UserEditDetails;

	public final static String URL_Gather_GatherHome = ControllerConstants.URL.Gather.GatherHome;
	public final static String URL_Gather_GatherAllCastleData = ControllerConstants.URL.Gather.GatherAllCastleData;
	public final static String URL_Gather_GatherSpecificServerCastleDataBase = ControllerConstants.URL.Gather.GatherSpecificServerCastleDataBase;
	public final static String URL_Gather_GatherSpecificServerCastleData = ControllerConstants.URL.Gather.GatherSpecificServerCastleData;
	public final static String URL_Gather_GatherAllPlayerCastleData = ControllerConstants.URL.Gather.GatherAllPlayerCastleData;
	public final static String URL_Gather_GatherSpecificPlayerCastleDataBase = ControllerConstants.URL.Gather.GatherSpecificPlayerCastleDataBase;
	public final static String URL_Gather_GatherSpecificPlayerCastleData = ControllerConstants.URL.Gather.GatherSpecificPlayerCastleData;
	public final static String URL_Gather_GatherLogs = ControllerConstants.URL.Gather.GatherLogs;
	
	public final static String URL_Report_ReportHome = ControllerConstants.URL.Report.ReportHome;
	public final static String URL_Report_CompareDivider = ControllerConstants.URL.Report.CompareDivider;
	public final static String URL_Report_OwnPlayerReportScorecard = ControllerConstants.URL.Report.OwnPlayerReportScorecard;
	public final static String URL_Report_PlayerReportScorecard = ControllerConstants.URL.Report.PlayerReportScorecard;
	public final static String URL_Report_PlayerReportCompareBase = ControllerConstants.URL.Report.PlayerReportCompareBase;
	public final static String URL_Report_PlayerReportCompare = ControllerConstants.URL.Report.PlayerReportCompare;
	public final static String URL_Report_OwnAllianceReportScorecard = ControllerConstants.URL.Report.OwnAllianceReportScorecard;
	public final static String URL_Report_AllianceReportScorecard = ControllerConstants.URL.Report.AllianceReportScorecard;
	public final static String URL_Report_AllianceReportCompareBase = ControllerConstants.URL.Report.AllianceReportCompareBase;
	public final static String URL_Report_AllianceReportCompare = ControllerConstants.URL.Report.AllianceReportCompare;
	public final static String URL_Report_OwnCastleReportScorecard = ControllerConstants.URL.Report.OwnCastleReportScorecard;
	public final static String URL_Report_CastleReportScorecard = ControllerConstants.URL.Report.CastleReportScorecard;
	public final static String URL_Report_CastleReportCompareBase = ControllerConstants.URL.Report.CastleReportCompareBase;
	public final static String URL_Report_CastleReportCompare = ControllerConstants.URL.Report.CastleReportCompare;
	
	public final static String URL_Login_Login = ControllerConstants.URL.Login.Login;
	public final static String URL_Login_LoginPostURL = ControllerConstants.URL.Login.LoginPostURL;
	public final static String URL_Login_LoginUpdatePassword = ControllerConstants.URL.Login.LoginUpdatePassword;
	public final static String URL_Login_LoginFailed = ControllerConstants.URL.Login.LoginFailed;
	public final static String URL_Login_Logout = ControllerConstants.URL.Login.Logout;
	public final static String URL_Login_Register = ControllerConstants.URL.Login.Register;
	public final static String URL_Login_Authenticate = ControllerConstants.URL.Login.Authenticate;

	public final static String URL_Admin_AdminHome = ControllerConstants.URL.Admin.AdminHome;
	public final static String URL_Admin_AdminEditServers = ControllerConstants.URL.Admin.AdminEditServers;
	public final static String URL_Admin_AdminEditUsers = ControllerConstants.URL.Admin.AdminEditUsers;
	public final static String URL_Admin_AdminTriggerEnsureIndexes = ControllerConstants.URL.Admin.AdminTriggerEnsureIndexes;
	public final static String URL_Admin_AdminCreateCSVData = ControllerConstants.URL.Admin.AdminCreateCSVData;

	public final static String URL_Maps_ImageFormat = ControllerConstants.URL.Maps.ImageFormat;
	public final static String URL_Maps_BaseMapName = ControllerConstants.URL.Maps.BaseMapName;
	public final static String URL_Maps_BaseMapRoot = ControllerConstants.URL.Maps.BaseMapRoot;
	public final static String URL_Maps_AllianceMapRoot = ControllerConstants.URL.Maps.AllianceMapRoot;
	public final static String URL_Maps_PlayerMapRoot = ControllerConstants.URL.Maps.PlayerMapRoot;
	public final static String URL_Maps_CastleMapRoot = ControllerConstants.URL.Maps.CastleMapRoot;
	public final static String URL_Maps_CompareMapRoot = ControllerConstants.URL.Maps.CompareMapRoot;

	public final static String URL_Maps_CompareDividerItem = ControllerConstants.URL.Maps.CompareDividerItem;
	public final static String URL_Maps_CompareDividerAttribute = ControllerConstants.URL.Maps.CompareDividerAttribute;

	public final static String URL_Search_SearchString = ControllerConstants.URL.PathVariable.SearchString;
	public final static String URL_Search_SearchPage = ControllerConstants.URL.PathVariable.SearchPage;

	public final static String URL_Search_SearchBase = ControllerConstants.URL.Search.SearchBase;
	public final static String URL_Search_SearchCastleBase = ControllerConstants.URL.Search.SearchCastleBase;
	public final static String URL_Search_SearchPlayerBase = ControllerConstants.URL.Search.SearchPlayerBase;
	public final static String URL_Search_SearchAllianceBase = ControllerConstants.URL.Search.SearchAllianceBase;
	public final static String URL_Search_TypeaheadCastleBase = ControllerConstants.URL.Search.TypeaheadCastleBase;
	public final static String URL_Search_TypeaheadPlayerBase = ControllerConstants.URL.Search.TypeaheadPlayerBase;
	public final static String URL_Search_TypeaheadAllianceBase = ControllerConstants.URL.Search.TypeaheadAllianceBase;
	public final static String URL_Search_CompareCastleBase = ControllerConstants.URL.Search.CompareCastleBase;
	public final static String URL_Search_ComparePlayerBase = ControllerConstants.URL.Search.ComparePlayerBase;
	public final static String URL_Search_CompareAllianceBase = ControllerConstants.URL.Search.CompareAllianceBase;

	public final static String URL_Tools_MapCreatorBase = ControllerConstants.URL.Tools.MapCreatorBase;
	public final static String URL_Tools_BattleCalculatorBase = ControllerConstants.URL.Tools.BattleCalculatorBase;
	
	public final static String URL_Suggestions_Suggestions = ControllerConstants.URL.Suggestions.Suggestions;
	
}
