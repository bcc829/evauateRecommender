package recommenderEvaluate;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MyRecommender extends GenericUserBasedRecommender {
	GenericUserBasedRecommender delegate;
	public MyRecommender(DataModel dataModel, UserNeighborhood neighborhood, UserSimilarity similarity) {
		super(dataModel, neighborhood, similarity);
		delegate = new GenericUserBasedRecommender(dataModel,neighborhood,similarity);
		// TODO Auto-generated constructor stub
	}


	@Override
	public float estimatePreference(long userID, long itemID) throws TasteException {
		// TODO Auto-generated method stub
		GenreRescorer rescorer = new GenreRescorer((int)userID);
		
		return (float)rescorer.rescore(itemID,delegate.estimatePreference(userID,itemID));
	}

	@Override
	protected FastIDSet getAllOtherItems(long userID, PreferenceArray preferencesFromUser, boolean includeKnownItems)
			throws TasteException {
		// TODO Auto-generated method stub
		return super.getAllOtherItems(userID, preferencesFromUser, includeKnownItems);
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany, boolean includeKnownItems) throws TasteException {
		// TODO Auto-generated method stub
		return super.recommend(userID, howMany, includeKnownItems);
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
		// TODO Auto-generated method stub
		rescorer = new GenreRescorer((int)userID);
		return super.recommend(userID, howMany, rescorer);
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
		GenreRescorer rescorer = new GenreRescorer((int)userID);
		return super.recommend(userID, howMany,rescorer);
	}

	@Override
	public void removePreference(long userID, long itemID) throws TasteException {
		// TODO Auto-generated method stub
		super.removePreference(userID, itemID);
	}

	@Override
	public void setPreference(long userID, long itemID, float value) throws TasteException {
		// TODO Auto-generated method stub
		super.setPreference(userID, itemID, value);
	}

}
