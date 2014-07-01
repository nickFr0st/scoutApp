package scout.rank;

import constants.RankConst;

/**
 * Created by Malloch on 6/8/14
 */
public class EmptyRank extends Rank {
    public EmptyRank() {
        super(RankConst.NO_RANK.getId(), RankConst.NO_RANK.getName(), RankConst.NO_RANK.getImgPath());
    }
}
