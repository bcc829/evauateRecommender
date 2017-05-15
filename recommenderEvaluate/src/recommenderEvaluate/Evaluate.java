package recommenderEvaluate;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.CachingUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Evaluate {

	public static void main(String[] args) throws TasteException {
		MysqlDataSource datasource = new MysqlDataSource();
		datasource.setServerName("localhost");
		datasource.setUser("root");
		datasource.setPassword("465651");
		datasource.setDatabaseName("tourOfAll");
		DataModel model = new ReloadFromJDBCDataModel(new MySQLJDBCDataModel(datasource, "evaluation", "User_Id", "Place_Id", "Score", null));
		AverageAbsoluteDifferenceRecommenderEvaluator recommenderEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		RecommenderBuilder builder = new RecommenderBuilder(){

			@Override
			public Recommender buildRecommender(DataModel model) throws TasteException {
				UserSimilarity similarity = new CachingUserSimilarity(new EuclideanDistanceSimilarity(model),model);
				UserNeighborhood neighborhood = new CachingUserNeighborhood(new ThresholdUserNeighborhood(0, similarity, model),model);
				return new MyRecommender(model,neighborhood,similarity);
			}
		
		};
		//70퍼센트를 모델 구축, 30퍼센트를 테스트
		recommenderEvaluator.evaluate(builder, null, model, 0.7, 1.0);
		System.out.println(recommenderEvaluator);
	}
	
}
