package recommenderEvaluate;

import java.util.ArrayList;

import org.apache.mahout.cf.taste.recommender.IDRescorer;



public class GenreRescorer implements IDRescorer {
	int UserId;
	ArrayList<String> userAttribute = new ArrayList<String>();

	public GenreRescorer(int id) {
		this.UserId = id;

		this.userAttribute = getUserAttributeInDB.returnPreferProperty(id);
	}

	@Override
	public boolean isFiltered(long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double rescore(long ItemId, double originalScore) {
		TravelDestination Td = null;
		try {
			// tourApi에서 아이템의 중분류값을 가져옴
			Td = new TravelDestination((int) ItemId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String str = Td.getAttribute();

		for (int i = 0; i < this.userAttribute.size(); i++) {
			if (str.equals(this.userAttribute.get(i))) {
				// 가중치 1.2배
				return originalScore * 1.2;
			}
		}
		return originalScore;
	}


}