package com.dynamoDbTest.demoDynamoDbTest.repositroies;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.dynamoDbTest.demoDynamoDbTest.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public User save(User user) {
        dynamoDBMapper.save(user);
        return user;
    }

    public User getUserById(String id) {
        return dynamoDBMapper.load(User.class, id);
    }

    public List<User> findAll() {
        return dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
    }

    public String deleteUserById(String id) {
        var user = dynamoDBMapper.load(User.class, id);
        dynamoDBMapper.delete(user);
        return "Deleted Successfully";
    }

    public String updateUser(String id, User user) {
        dynamoDBMapper.save(user, new DynamoDBSaveExpression()
                .withExpectedEntry("id",
                        new ExpectedAttributeValue(new AttributeValue().withS(id))));

        return "Successfully updated";
    }

    // Normal Query
    public List<User> getUsersWithId(String id) {
        try {
            DynamoDBQueryExpression<User> query = new DynamoDBQueryExpression<User>()
                    .withHashKeyValues(User.builder().id(id).build())
                    .withLimit(10); //optional
            return dynamoDBMapper.query(User.class, query);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("");
        }
    }

    // Query with global secondary index (GSI)
    public List<User> getUsersWithGsi(String date) {
        try {
            DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                    .withHashKeyValues(User.builder().date(date).build()) // use your gsi partition key
                    .withIndexName("date-index")  // need it when you query from gsi
                    .withConsistentRead(false)
                    // In DynamoDB, reads can be either strongly consistent or eventually consistent:
                    // Strongly Consistent Read: Ensures that you get the most up-to-date data, but at a higher cost and potentially higher latency.
                    // Eventually Consistent Read: Returns data that might not reflect the most recent changes immediately after an update but is faster and cheaper. Setting false here means the query will use eventual consistency.
                    .withLimit(10);
            return dynamoDBMapper.query(User.class, queryExpression);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("");
        }
    }


}
