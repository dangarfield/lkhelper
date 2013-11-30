package com.dangarfield.lkhelper.data.report;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllianceLeaderboardData implements Comparable<AllianceLeaderboardData> {

	@JsonProperty("_id")
	private int id;
	private String name;
	private int pointsStart;
	private int pointsEnd;
	private int pointsGrowth;
	private int castleCountStart;
	private int castleCountEnd;
	private int castleCountGrowth;
	private int rankStart;
	private int rankEnd;
	private int rankGrowth;
	private int allianceRankStart;
	private int allianceRankEnd;
	private int allianceRankGrowth;
	
	/**
	 * 
	 */
	public AllianceLeaderboardData() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pointsStart
	 */
	public int getPointsStart() {
		return pointsStart;
	}

	/**
	 * @param pointsStart the pointsStart to set
	 */
	public void setPointsStart(int pointsStart) {
		this.pointsStart = pointsStart;
	}

	/**
	 * @return the pointsEnd
	 */
	public int getPointsEnd() {
		return pointsEnd;
	}

	/**
	 * @param pointsEnd the pointsEnd to set
	 */
	public void setPointsEnd(int pointsEnd) {
		this.pointsEnd = pointsEnd;
	}

	/**
	 * @return the pointsGrowth
	 */
	public int getPointsGrowth() {
		return pointsGrowth;
	}

	/**
	 * @param pointsGrowth the pointsGrowth to set
	 */
	public void setPointsGrowth(int pointsGrowth) {
		this.pointsGrowth = pointsGrowth;
	}

	/**
	 * @return the castleCountStart
	 */
	public int getCastleCountStart() {
		return castleCountStart;
	}

	/**
	 * @param castleCountStart the castleCountStart to set
	 */
	public void setCastleCountStart(int castleCountStart) {
		this.castleCountStart = castleCountStart;
	}

	/**
	 * @return the castleCountEnd
	 */
	public int getCastleCountEnd() {
		return castleCountEnd;
	}

	/**
	 * @param castleCountEnd the castleCountEnd to set
	 */
	public void setCastleCountEnd(int castleCountEnd) {
		this.castleCountEnd = castleCountEnd;
	}

	/**
	 * @return the castleCountGrowth
	 */
	public int getCastleCountGrowth() {
		return castleCountGrowth;
	}

	/**
	 * @param castleCountGrowth the castleCountGrowth to set
	 */
	public void setCastleCountGrowth(int castleCountGrowth) {
		this.castleCountGrowth = castleCountGrowth;
	}

	/**
	 * @return the rankStart
	 */
	public int getRankStart() {
		return rankStart;
	}

	/**
	 * @param rankStart the rankStart to set
	 */
	public void setRankStart(int rankStart) {
		this.rankStart = rankStart;
	}

	/**
	 * @return the rankEnd
	 */
	public int getRankEnd() {
		return rankEnd;
	}

	/**
	 * @param rankEnd the rankEnd to set
	 */
	public void setRankEnd(int rankEnd) {
		this.rankEnd = rankEnd;
	}

	/**
	 * @return the rankGrowth
	 */
	public int getRankGrowth() {
		return rankGrowth;
	}

	/**
	 * @param rankGrowth the rankGrowth to set
	 */
	public void setRankGrowth(int rankGrowth) {
		this.rankGrowth = rankGrowth;
	}

	/**
	 * @return the allianceRankStart
	 */
	public int getAllianceRankStart() {
		return allianceRankStart;
	}

	/**
	 * @param allianceRankStart the allianceRankStart to set
	 */
	public void setAllianceRankStart(int allianceRankStart) {
		this.allianceRankStart = allianceRankStart;
	}

	/**
	 * @return the allianceRankEnd
	 */
	public int getAllianceRankEnd() {
		return allianceRankEnd;
	}

	/**
	 * @param allianceRankEnd the allianceRankEnd to set
	 */
	public void setAllianceRankEnd(int allianceRankEnd) {
		this.allianceRankEnd = allianceRankEnd;
	}

	/**
	 * @return the allianceRankGrowth
	 */
	public int getAllianceRankGrowth() {
		return allianceRankGrowth;
	}

	/**
	 * @param allianceRankGrowth the allianceRankGrowth to set
	 */
	public void setAllianceRankGrowth(int allianceRankGrowth) {
		this.allianceRankGrowth = allianceRankGrowth;
	}

	@Override
	public int compareTo(AllianceLeaderboardData o) {
		return String.valueOf(getName()).compareTo(String.valueOf(o.getName()));
	}

	public static Comparator<AllianceLeaderboardData> Points = new Comparator<AllianceLeaderboardData>() {
		public int compare(AllianceLeaderboardData one, AllianceLeaderboardData two) {
			return Integer.valueOf(two.getPointsEnd()).compareTo(Integer.valueOf(one.getPointsEnd()));
		}
	};
	public static Comparator<AllianceLeaderboardData> PointsGrowth = new Comparator<AllianceLeaderboardData>() {
		public int compare(AllianceLeaderboardData one, AllianceLeaderboardData two) {
			return Integer.valueOf(two.getPointsGrowth()).compareTo(Integer.valueOf(one.getPointsGrowth()));
		}
	};

	public static Comparator<AllianceLeaderboardData> CastleCount = new Comparator<AllianceLeaderboardData>() {
		public int compare(AllianceLeaderboardData one, AllianceLeaderboardData two) {
			return Integer.valueOf(two.getCastleCountEnd()).compareTo(Integer.valueOf(one.getCastleCountEnd()));
		}
	};
	public static Comparator<AllianceLeaderboardData> CastleCountGrowth = new Comparator<AllianceLeaderboardData>() {
		public int compare(AllianceLeaderboardData one, AllianceLeaderboardData two) {
			return Integer.valueOf(two.getCastleCountGrowth()).compareTo(Integer.valueOf(one.getCastleCountGrowth()));
		}
	};

	public static Comparator<AllianceLeaderboardData> Rank = new Comparator<AllianceLeaderboardData>() {
		public int compare(AllianceLeaderboardData one, AllianceLeaderboardData two) {
			return Integer.valueOf(one.getRankEnd()).compareTo(Integer.valueOf(two.getRankEnd()));
		}
	};
	public static Comparator<AllianceLeaderboardData> RankGrowth = new Comparator<AllianceLeaderboardData>() {
		public int compare(AllianceLeaderboardData one, AllianceLeaderboardData two) {
			return Integer.valueOf(two.getRankGrowth()).compareTo(Integer.valueOf(one.getRankGrowth()));
		}
	};
	
	public static Comparator<AllianceLeaderboardData> AllianceRank = new Comparator<AllianceLeaderboardData>() {
		public int compare(AllianceLeaderboardData one, AllianceLeaderboardData two) {
			return Integer.valueOf(one.getAllianceRankEnd()).compareTo(Integer.valueOf(two.getAllianceRankEnd()));
		}
	};
	public static Comparator<AllianceLeaderboardData> AllianceRankGrowth = new Comparator<AllianceLeaderboardData>() {
		public int compare(AllianceLeaderboardData one, AllianceLeaderboardData two) {
			return Integer.valueOf(two.getAllianceRankGrowth()).compareTo(Integer.valueOf(one.getAllianceRankGrowth()));
		}
	};
	
	@Override
	public String toString() {
		return this.name + " - (" + String.valueOf(this.id) + ")";
	}
	
	
}
