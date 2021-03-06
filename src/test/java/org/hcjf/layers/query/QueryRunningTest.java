package org.hcjf.layers.query;

import org.hcjf.bson.BsonDecoder;
import org.hcjf.bson.BsonDocument;
import org.hcjf.bson.BsonEncoder;
import org.hcjf.layers.Layer;
import org.hcjf.layers.Layers;
import org.hcjf.layers.crud.ReadRowsLayerInterface;
import org.hcjf.layers.query.functions.BaseQueryFunctionLayer;
import org.hcjf.layers.query.functions.QueryFunctionLayerInterface;
import org.hcjf.properties.SystemProperties;
import org.hcjf.utils.Introspection;
import org.hcjf.utils.JsonUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author javaito
 */
public class QueryRunningTest {

    private static final String CHARACTER = "character";
    private static final String CHARACTER_2 = "character2";
    private static final String ADDRESS = "address";

    private static final String ID = "id";

    private static final String NAME = "name";
    private static final String LAST_NAME = "lastName";
    private static final String NICKNAME = "nickname";
    private static final String BIRTHDAY = "birthday";
    private static final String WEIGHT = "weight";
    private static final String HEIGHT = "height";
    private static final String GENDER = "gender";
    private static final String ADDRESS_ID = "addressId";
    private static final String BODY = "body";

    private static final String STREET = "street";
    private static final String NUMBER = "number";

    private static final Map<UUID, JoinableMap> simpsonCharacters = new HashMap<>();
    private static final Map<UUID, JoinableMap> simpsonCharacters2 = new HashMap<>();
    private static final Map<UUID, JoinableMap> simpsonAddresses = new HashMap<>();
    private static final TestDataSource dataSource = new TestDataSource();

    @BeforeClass
    public static void config() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            JoinableMap map = new JoinableMap(ADDRESS);
            UUID addressId = UUID.randomUUID();
            map.put(ADDRESS_ID, addressId);
            map.put(STREET, "Evergreen Terrace");
            map.put(NUMBER, 742);
            simpsonAddresses.put(addressId, map);

            map = new JoinableMap(CHARACTER);
            map.put(ID, UUID.randomUUID());
            map.put(NAME, "Homer Jay");
            map.put(LAST_NAME, "Simpson");
            map.put(BIRTHDAY, dateFormat.parse("1981-02-19"));
            map.put(WEIGHT, 108.0);
            map.put(HEIGHT, 1.83);
            map.put(GENDER, Gender.MALE);
            map.put(ADDRESS_ID, addressId);
            simpsonCharacters.put((UUID) map.get(ID), map);
            simpsonCharacters2.put((UUID) map.get(ID), map);

            map = new JoinableMap(CHARACTER);
            map.put(ID, UUID.randomUUID());
            map.put(NAME, "Marjorie Jaqueline");
            map.put(LAST_NAME, "Bouvier Simpson");
            map.put(NICKNAME, "Marge");
            map.put(BIRTHDAY, dateFormat.parse("1981-03-14"));
            map.put(WEIGHT, 67.0);
            map.put(HEIGHT, 1.65);
            map.put(GENDER, Gender.FEMALE);
            map.put(ADDRESS_ID, addressId);
            simpsonCharacters.put((UUID) map.get(ID), map);
            simpsonCharacters2.put((UUID) map.get(ID), map);

            BsonDocument document = new BsonDocument();
            document.put("field1", "string");
            document.put("field2", new Date());
            document.put("field3", 5);

            map = new JoinableMap(CHARACTER);
            map.put(ID, UUID.randomUUID());
            map.put(NAME, "Bartolomeo Jay");
            map.put(LAST_NAME, "Simpson Bouvier");
            map.put(NICKNAME, "Bart");
            map.put(BIRTHDAY, dateFormat.parse("2007-03-14"));
            map.put(WEIGHT, 45.0);
            map.put(HEIGHT, 1.20);
            map.put(GENDER, Gender.MALE);
            map.put(ADDRESS_ID, addressId);
            map.put(BODY, BsonEncoder.encode(document));
            simpsonCharacters.put((UUID) map.get(ID), map);
            simpsonCharacters2.put((UUID) map.get(ID), map);

            map = new JoinableMap(CHARACTER);
            map.put(ID, UUID.randomUUID());
            map.put(NAME, "Lisa Marie");
            map.put(LAST_NAME, "Simpson Bouvier");
            map.put(BIRTHDAY, dateFormat.parse("2009-07-20"));
            map.put(WEIGHT, 37.0);
            map.put(HEIGHT, 1.05);
            map.put(GENDER, Gender.FEMALE);
            map.put(ADDRESS_ID, addressId);
            simpsonCharacters.put((UUID) map.get(ID), map);
            simpsonCharacters2.put((UUID) map.get(ID), map);

            map = new JoinableMap(CHARACTER);
            map.put(ID, UUID.randomUUID());
            map.put(NAME, "Margaret Abigail");
            map.put(LAST_NAME, "Simpson Bouvier");
            map.put(NICKNAME, "Maggie");
            map.put(BIRTHDAY, dateFormat.parse("2015-09-02"));
            map.put(WEIGHT, 15.0);
            map.put(HEIGHT, 0.75);
            map.put(GENDER, Gender.FEMALE);
            map.put(ADDRESS_ID, addressId);
            simpsonCharacters.put((UUID) map.get(ID), map);
            simpsonCharacters2.put((UUID) map.get(ID), map);

            map = new JoinableMap(ADDRESS);
            addressId = UUID.randomUUID();
            map.put(ADDRESS_ID, addressId);
            map.put(STREET, "Buenos Aires");
            map.put(NUMBER, 1025);
            simpsonAddresses.put(addressId, map);

            map = new JoinableMap(CHARACTER);
            map.put(ID, UUID.randomUUID());
            map.put(NAME, "Maurice Lester");
            map.put(LAST_NAME, "Szyslak");
            map.put(NICKNAME, "Moe");
            map.put(BIRTHDAY, dateFormat.parse("1975-03-14"));
            map.put(WEIGHT, 82.0);
            map.put(HEIGHT, 1.70);
            map.put(GENDER, Gender.MALE);
            map.put(ADDRESS_ID, addressId);
            simpsonCharacters.put((UUID) map.get(ID), map);
            simpsonCharacters2.put((UUID) map.get(ID), map);

            map = new JoinableMap(ADDRESS);
            addressId = UUID.randomUUID();
            map.put(ADDRESS_ID, addressId);
            map.put(STREET, "Chubut");
            map.put(NUMBER, 2321);
            simpsonAddresses.put(addressId, map);

            map = new JoinableMap(CHARACTER);
            map.put(ID, UUID.randomUUID());
            map.put(NAME, "Nedward");
            map.put(LAST_NAME, "Flanders");
            map.put(NICKNAME, "Ned");
            map.put(BIRTHDAY, dateFormat.parse("1975-03-14"));
            map.put(WEIGHT, 82.0);
            map.put(HEIGHT, 1.70);
            map.put(GENDER, Gender.MALE);
            simpsonCharacters.put((UUID) map.get(ID), map);
            simpsonCharacters2.put((UUID) map.get(ID), map);
        } catch (Exception ex){}

        Layers.publishLayer(CharacterResource.class);
        Layers.publishLayer(Character2Resource.class);
        Layers.publishLayer(AddressResource.class);
    }

    public enum Gender {

        MALE,

        FEMALE

    }

    private static class TestDataSource implements Queryable.DataSource<JoinableMap> {

        @Override
        public Collection<JoinableMap> getResourceData(Queryable queryable) {
            Collection<JoinableMap> result = new HashSet<>();

            switch (queryable.getResourceName()) {
                case CHARACTER: {
                    for(JoinableMap map : simpsonCharacters.values()) {
                        result.add(new JoinableMap(map));
                    }
                    break;
                }
                case CHARACTER_2: {
                    for(JoinableMap map : simpsonCharacters2.values()) {
                        result.add(new JoinableMap(map));
                    }
                    break;
                }
                case ADDRESS: {
                    for(JoinableMap map : simpsonAddresses.values()) {
                        result.add(new JoinableMap(map));
                    }
                    break;
                }
                default:{
                    throw new IllegalArgumentException("Resource not found " + queryable.getResourceName());
                }
            }

            return result;
        }

    }

    @Test
    public void innerQuery() {
        Query query = Query.compile("SELECT * FROM character WHERE (SELECT name FROM character2 WHERE name like 'Homer') like name");
        Collection<JoinableMap> resultSet = Query.evaluate(query);
        System.out.println();

        query = Query.compile("SELECT * FROM character WHERE concat((SELECT name FROM character2 WHERE name like 'Homer'), ' Jay') like name");
        resultSet = Query.evaluate(query);
        System.out.println();

        query = Query.compile("select addressId from address where street like 'Evergreen Terrace' limit 1");
        resultSet = Query.evaluate(query);
        System.out.println();

        query = Query.compile("SELECT name FROM character WHERE addressId = (select addressId from address where street like 'Evergreen Terrace' limit 1)");
        resultSet = Query.evaluate(query);
        System.out.println();

        query = Query.compile("SELECT * FROM character WHERE (SELECT name FROM character2 WHERE addressId = (select addressId from address where street like 'Evergreen Terrace' limit 1) limit 1) like name");
        resultSet = Query.evaluate(query);
        System.out.println();
    }

    @Test
    public void subQueryAsParam() {
        Query query = Query.compile("SELECT * FROM character WHERE addressId = (SELECT addressId FROM address where street like 'Evergreen')");
        Collection<JoinableMap> resultSet = Query.evaluate(query);
        System.out.println();
    }

    @Test
    public void aggregateFunction() {
        Query query = Query.compile("SELECT addressId, aggregateProduct(weight) as aggregateWeight FROM character group by addressId");
        Collection<JoinableMap> resultSet = Query.evaluate(query);
        query = Query.compile("SELECT addressId, aggregateSum(weight) as aggregateWeight, aggregateSum(height) as aggregateHeight, aggregateEvalExpression(aggregateWeight - aggregateHeight) as result FROM character group by addressId");
        Collection<JoinableMap> resultSet1 = Query.evaluate(query);
        query = Query.compile("SELECT addressId, aggregateEvalExpression(sum(weight) / 2) as aggregateWeight FROM character group by addressId");
        Collection<JoinableMap> resultSet2 = Query.evaluate(query);
        System.out.println();
        query = Query.compile("SELECT addressId, aggregateEvalExpression(sum(weight) / 2) as aggregateWeight, aggregateContext(numberFormat('$#,###.00', aggregateWeight)) as weightFormatted FROM character group by addressId");
        Collection<JoinableMap> resultSet3 = Query.evaluate(query);
        System.out.println();
    }

    @Test
    public void checkToString() {
        Query query = Query.compile("SELECT * FROM character WHERE @underlying('field')");
        String queryToString = query.toString();
        Assert.assertTrue(queryToString.contains("@underlying('field')"));
    }

    @Test
    public void queryDynamicResource() {
        Query query1 = Query.compile("SELECT * FROM character JOIN address ON address.addressId = character.addressId where lastName like 'Simpson'");
        Collection<JoinableMap> resultSet1 = Query.evaluate(query1);
        Query query2 = Query.compile("SELECT * FROM (SELECT * FROM character JOIN address ON address.addressId = character.addressId where lastName like 'Simpson') as ch WHERE weight > 16");
        Collection<JoinableMap> resultSet2 = Query.evaluate(query2);
        System.out.printf("");
        Query query3 = Query.compile("SELECT * FROM (SELECT * FROM (SELECT * FROM character WHERE toString(gender) = 'FEMALE') as ch1 where lastName like 'Simpson') as ch JOIN address ON address.addressId = ch.addressId WHERE weight > 16");
        Collection<JoinableMap> resultSet3 = Query.evaluate(query3);
        System.out.println();
        Query query4 = Query.compile("SELECT * FROM (SELECT *, bsonParse(body) AS bodyDecoded  FROM character where isNotNull(body)).bodyDecoded as body");
        Collection<JoinableMap> resultSet4 = Query.evaluate(query4);
        Assert.assertEquals(Introspection.resolve(resultSet4.stream().findFirst().get(), "field1"), "string");
        Query query5 = Query.compile("SELECT field1 FROM (SELECT *, bsonParse(body) AS bodyDecoded  FROM character where isNotNull(body)).bodyDecoded as body");
        Collection<JoinableMap> resultSet5 = Query.evaluate(query5);
        Assert.assertEquals(resultSet5.stream().findFirst().get().size(), 1);
        Query query6 = Query.compile("SELECT * FROM character JOIN (select * from address) as add ON add.addressId = character.addressId WHERE weight > 16");
        Collection<JoinableMap> resultSet6 = Query.evaluate(query6);
        System.out.println();
    }

    @Test
    public void underlyingFunction() {
        Query query = Query.compile("SELECT * FROM character WHERE @underlying()");
        Collection<JoinableMap> resultSet = Query.evaluate(query);
        Assert.assertEquals(resultSet.size(), 7);

        query = Query.compile("SELECT * FROM character WHERE @underlying() and @underlying2()");
        resultSet = Query.evaluate(query);
        Assert.assertEquals(resultSet.size(), 7);

        query = Query.compile("SELECT * FROM character WHERE (@underlying() or @underlying2())");
        resultSet = Query.evaluate(query);
        Assert.assertEquals(resultSet.size(), 7);
    }

    @Test
    public void nullValues() {
        Query query = Query.compile("SELECT * FROM character WHERE  name = null");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 0);

        query = Query.compile("SELECT * FROM character WHERE  nickname like 'Bart'");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 1);

        query = Query.compile("SELECT * FROM character WHERE  nickname = null");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 2);

        query = Query.compile("SELECT * FROM character WHERE  nickname != null");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 5);
    }

    @Test
    public void distinct() {
        Query query = Query.compile("SELECT lastName, distinct(lastName) FROM character");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 5);

        query = Query.compile("SELECT count(lastName) as value1, distinct(lastName), count(lastName) as value2 FROM character group by a");
        resultSet = query.evaluate(dataSource);
        System.out.println();
    }

    @Test
    public void placeHolder() {
        Query query = Query.compile("SELECT name FROM character WHERE name = ?");
        ParameterizedQuery parameterizedQuery = query.getParameterizedQuery().add("Margaret Abigail");
        Collection<JoinableMap> resultSet = parameterizedQuery.evaluate(dataSource);
        System.out.println();
    }

    @Test
    public void startAndLimit() {
        Query query = Query.compile("SELECT name FROM character ORDER BY name");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);

        Collection<JoinableMap> firstResultSet = resultSet;
        JoinableMap first = resultSet.stream().findFirst().get();
        JoinableMap last = resultSet.stream().skip(resultSet.stream().count() - 1).findFirst().get();

        query = Query.compile("SELECT * FROM character ORDER BY name LIMIT 1");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.stream().findFirst().get().get("name"), first.get("name"));

        query = Query.compile("SELECT * FROM character ORDER BY name DESC LIMIT 1");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.stream().findFirst().get().get("name"), last.get("name"));

        query = Query.compile("SELECT * FROM character ORDER BY name LIMIT 1000");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), firstResultSet.size());

        query = Query.compile("SELECT * FROM character ORDER BY name START 0 LIMIT 1000");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), firstResultSet.size());

        query = Query.compile("SELECT * FROM character ORDER BY name START 2 LIMIT 1000");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), firstResultSet.size()-2);

        query = Query.compile("SELECT * FROM character ORDER BY name START 2 LIMIT 2");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 2);
    }

    @Test
    public void debug() {
        Query query = Query.compile("" +
                "SELECT address.street, lastName, concat(name), stringJoin('@', name), sum(weight), addressId FROM character " +
                "JOIN address on address.addressId = character.addressId " +
                "WHERE character.lastName like 'simp'");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);
        System.out.println();
    }

    @Test
    public void join() {
        Query query = Query.compile("SELECT address.street, concat(name), stringJoin('@', name), sum(weight), addressId FROM character JOIN address ON address.addressId = character.addressId GROUP BY addressId");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonAddresses.size() - 1);

        query = Query.compile("SELECT name, count(character.name), address.street, concat(name), stringJoin('@', name), sum(weight), addressId FROM character JOIN address ON address.addressId = character.addressId GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonAddresses.size() - 1);

        query = Query.compile("SELECT * FROM character RIGHT JOIN address ON address.addressId = character.addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size());

        query = Query.compile("SELECT * FROM character LEFT JOIN address ON address.addressId = character.addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size());

        query = Query.compile("SELECT * FROM character FULL JOIN address ON address.addressId = character.addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size() + 1);

        query = Query.compile("SELECT * FROM character JOIN address ON address.addressId = character.addressId where isNotNull(nickname)");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 4);

        query = Query.compile("SELECT * FROM character JOIN address ON address.addressId = character.addressId where isNotNull(character.nickname)");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 4);

        query = Query.compile("SELECT * FROM character JOIN address ON address.addressId = character.addressId where isNotNull(nickname) and street = 'Evergreen Terrace'");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 3);

        query = Query.compile("SELECT * FROM character JOIN character2 ON character.id = character2.id");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size());

        query = Query.compile("SELECT * FROM character JOIN character2 ON true");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size() * simpsonCharacters.size());

        query = Query.compile("SELECT * FROM character JOIN character2 ON character.id = character2.id JOIN address ON address.addressId = character.addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size() - 1);

        query = Query.compile("SELECT * FROM character JOIN character2 ON character.id = character2.id LEFT JOIN address ON address.addressId = character.addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size());

        query = Query.compile("SELECT * FROM character JOIN character2 ON character.lastName like character2.lastName JOIN address ON address.addressId = character.addressId");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT * FROM character JOIN character2 ON character.lastName like character2.lastName JOIN address ON address.addressId = character.addressId");
        resultSet = query.evaluate(dataSource);
        System.out.println();

        query = Query.compile("SELECT name, aggregateSum(weight) FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        System.out.println();
    }

    @Test
    public void select() {
        SystemProperties.get(SystemProperties.Service.SYSTEM_SESSION_NAME);

        Query query = Query.compile("SELECT * FROM character");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size());

        query = Query.compile("SELECT * FROM credentials WHERE methodName = 'user-password' AND get(fields, 'javaito') = '1234'");
        System.out.println();

        query = Query.compile("SELECT * FROM character ORDER BY addressId, name DESC");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT count(*) FROM character");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.iterator().next().get("count(*)"), simpsonCharacters.size());

        query = Query.compile("SELECT count(*) AS size FROM character");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.iterator().next().get("size"), simpsonCharacters.size());

        query = Query.compile("SELECT count(weight) AS size FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 3);

        query = Query.compile("SELECT count(weight) AS size FROM character WHERE isNotNull(addressId) GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 2);

        query = Query.compile("SELECT aggregateSum(weight) AS sum FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 3);

        query = Query.compile("SELECT aggregateProduct(weight) AS product FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 3);

        query = Query.compile("SELECT aggregateMean(weight) AS mean FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), 3);

        query = Query.compile("SELECT now(), getYear(birthday), periodInDays(birthday), getMonth(birthday) FROM character");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size());

        query = Query.compile("SELECT * FROM character GROUP BY getMonth(birthday)");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT weight, 2  *  weight AS superWeight, pow(max(integerValue(weight), integerValue(50.1)) ,2) AS smartWeight FROM character");
        resultSet = query.evaluate(dataSource);
        for(JoinableMap joinableMap : resultSet) {
            if(((Number)joinableMap.get(WEIGHT)).doubleValue() > 50) {
                Assert.assertTrue(((Number)joinableMap.get("smartWeight")).doubleValue() > 2500);
            } else {
                Assert.assertTrue(((Number)joinableMap.get("smartWeight")).intValue() == 2500);
            }
        }

        query = Query.compile("SELECT weight, -2  *  weight AS superWeight, pow(max(weight, 50.1) ,2) AS smartWeight FROM character");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT name FROM character");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.iterator().next().size(), 1);

        query = Query.compile("SELECT *, name as nombre FROM character");
        resultSet = query.evaluate(dataSource);
        JoinableMap first = resultSet.iterator().next();
        Assert.assertEquals(first.get("nombre"), first.get("name"));

        query = Query.compile("SELECT street, concat(name), stringJoin('@', name), sum(weight), addressId FROM character JOIN address ON address.addressId = character.addressId GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonAddresses.size() - 1);

        query = Query.compile("SELECT bsonParse(body) AS body FROM character WHERE name LIKE 'Bartolomeo'");
        resultSet = query.evaluate(dataSource);
        Assert.assertEquals(((Map<String,Object>)resultSet.iterator().next().get(BODY)).get("field1"), "string");

        query = Query.compile("SELECT get(bsonParse(body),'field1') AS body FROM character WHERE name LIKE 'Bartolomeo'");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT bsonParse(body) AS body FROM character WHERE name LIKE ?");
        ParameterizedQuery parameterizedQuery = query.getParameterizedQuery();
        resultSet = parameterizedQuery.add("Bartolomeo").evaluate(dataSource);
        Assert.assertEquals(((Map<String,Object>)resultSet.iterator().next().get(BODY)).get("field1"), "string");

        query = Query.compile("SELECT * FROM character WHERE weight > ? AND weight < ?");
        parameterizedQuery = query.getParameterizedQuery();
        resultSet = parameterizedQuery.add(40).add(100).evaluate(dataSource);
        for(JoinableMap row : resultSet){
            Assert.assertTrue((double)row.get("weight") > 40 && (double)row.get("weight") < 100);
        }

        resultSet = parameterizedQuery.add(40).add(80).evaluate(dataSource);
        for(JoinableMap row : resultSet){
            Assert.assertTrue((double)row.get("weight") > 40 && (double)row.get("weight") < 80);
        }

        query = Query.compile("SELECT * FROM character WHERE weight >= ? AND weight <= ? AND true");
        parameterizedQuery = query.getParameterizedQuery();
        resultSet = parameterizedQuery.add(40).add(108).evaluate(dataSource);

        query = Query.compile("SELECT * FROM character WHERE weight < ? OR weight > ?");
        query.toString();
        parameterizedQuery = query.getParameterizedQuery();
        resultSet = parameterizedQuery.add(40).add(100).evaluate(dataSource);
        for(JoinableMap row : resultSet){
            Assert.assertTrue((double)row.get("weight") < 40 || (double)row.get("weight") > 100);
        }

        Layers.publishLayer(CustomFunction.class);

        query = Query.compile("SELECT name, customFunction(integerValue(weight)) FROM character");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT dateFormat(birthday, 'YYYY--MM--dd HH::mm::ss') as year FROM character");
        resultSet = query.evaluate(dataSource);
        Assert.assertTrue(resultSet.iterator().next().get("year") instanceof String);

        query = Query.compile("SELECT addressId, aggregateSum(weight) AS sum FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        System.out.println(JsonUtils.toJsonTree(resultSet).toString());

        query = Query.compile("SELECT name, if(weight + 10 > 100, 'gordo', 'flaco') AS es FROM character");
        resultSet = query.evaluate(dataSource);
        for(JoinableMap map : resultSet) {
            Assert.assertNotNull(map.get("es"));
        }

        query = Query.compile("SELECT name, if(equals(name, 'Homer Jay'), 'gordo', 'flaco') AS es FROM character");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT name, case(name, 'Homer Jay', 'gordo', 'Marjorie Jaqueline', 'flaco', 'mmm!') AS es FROM character");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT lastName, count(weight) as size, aggregateMin(weight) as min, aggregateMax(weight) as max, aggregateSum(weight) as sum, aggregateMean(weight) as arithmeticMean, aggregateMean(weight, 'harmonic') as harmonicMean FROM character group by lastName");
        resultSet = query.evaluate(dataSource);
        System.out.println(JsonUtils.toJsonTree(resultSet).toString());
        System.out.println();

        query = Query.compile("SELECT name, lastName, nickname FROM character");
        resultSet = query.evaluate(dataSource);
        System.out.println(JsonUtils.toJsonTree(resultSet).toString());
        System.out.println();

        query = Query.compile("SELECT name, lastName, nickname, new('literal') as literal FROM character");
        resultSet = query.evaluate(dataSource);
        System.out.println(JsonUtils.toJsonTree(resultSet).toString());
        System.out.println();

        query = Query.compile("SELECT name, lastName, nickname, new('2019-01-01 00:00:00') as literal FROM character");
        resultSet = query.evaluate(dataSource);
        Assert.assertTrue(resultSet.stream().findFirst().get().get("literal") instanceof Date);
        System.out.println(JsonUtils.toJsonTree(resultSet).toString());
        System.out.println();

        query = Query.compile("SELECT name, lastName, nickname, getMillisecondUnixEpoch(new('2019-01-01 00:00:00')) as literal FROM character");
        resultSet = query.evaluate(dataSource);
        Assert.assertTrue(resultSet.stream().findFirst().get().get("literal") instanceof Long);

        query = Query.compile("SELECT length(name) as length, length(name) + 5 as lengthName FROM character");
        resultSet = query.evaluate(dataSource);

        BsonDocument bsonDocument = new BsonDocument(resultSet.stream().findFirst().get());
        byte[] doc = BsonEncoder.encode(bsonDocument);

        bsonDocument = BsonDecoder.decode(doc);
        Map<String,Object> map = bsonDocument.toMap();
    }

    @Test
    public void isNullTest() {
        Query query = Query.compile("SELECT isNull(noField) as n FROM character");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);
        Assert.assertEquals(resultSet.size(), simpsonCharacters.size());
        Assert.assertTrue(Introspection.resolve(resultSet.stream().findFirst().get(), "n"));
    }

    @Test
    public void testOrder() {
        Query query = Query.compile("SELECT * FROM character ORDER BY name limit 1");
        Collection<JoinableMap> resultSet = Query.evaluate(query);
        System.out.println();
    }

    @Test
    public void testGeoFunctions() {

        Query query = Query.compile("SELECT name, geoAsText(geoUnion('POINT(-33.2569 -65.2548)', 'MULTIPOINT ((10 40), (40 30), (20 20), (30 10))')) as gulf FROM character");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT name, geoUnion('POINT(-33.2569 -65.2548)', 'MULTIPOINT ((10 40), (40 30), (20 20), (30 10))') as gulf FROM character");
        resultSet = query.evaluate(dataSource);

        query = Query.compile("SELECT name, new('hola') as gulf FROM character");
        resultSet = query.evaluate(dataSource);

        System.out.println();
    }

    @Test
    public void testToStringFunction() {
        Query query = Query.compile("SELECT toString(name) FROM character");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);
        System.out.println();

        query = Query.compile("SELECT toString(number) FROM address");
        resultSet = query.evaluate(dataSource);
        System.out.println();

        query = Query.compile("SELECT * FROM (SELECT number as A_NUMBER FROM address) as add WHERE toString(A_NUMBER) = '2321'");
        resultSet = query.evaluate(dataSource);
        System.out.println();
    }

    @Test
    public void testCollectionFunctions() {
        Query query = Query.compile("SELECT *, aggregateContext(sort(name)) as sortedNames FROM character GROUP BY addressId");
        Collection<JoinableMap> resultSet = query.evaluate(dataSource);
        for(JoinableMap row : resultSet) {
            if(((Collection)row.get("sortedNames")).size() > 1) {
                Assert.assertEquals(((Collection)row.get("sortedNames")).stream().findFirst().get(), "Bartolomeo Jay");
            }
        }

        query = Query.compile("SELECT *, aggregateContext(first(sort(name))) as firstSortedName FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        for(JoinableMap row : resultSet) {
            Assert.assertTrue(row.get("firstSortedName") instanceof String);
        }

        query = Query.compile("SELECT *, aggregateContext(last(sort(name))) as firstSortedName FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        for(JoinableMap row : resultSet) {
            Assert.assertTrue(row.get("firstSortedName") instanceof String);
        }

        query = Query.compile("SELECT *, aggregateContext(limit(name, 3)) as limitedNames FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        for(JoinableMap row : resultSet) {
            Assert.assertTrue(row.get("limitedNames") instanceof Collection);
            Assert.assertTrue(((Collection)row.get("limitedNames")).size() <= 3);
        }

        query = Query.compile("SELECT *, aggregateContext(skip(name, 3)) as limitedNames FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        for(JoinableMap row : resultSet) {
            Assert.assertTrue(row.get("limitedNames") instanceof Collection);
        }

        query = Query.compile("SELECT *, aggregateContext(limit(skip(sort(name), 2), 1)) as limitedNames FROM character GROUP BY addressId");
        resultSet = query.evaluate(dataSource);
        for(JoinableMap row : resultSet) {
            Assert.assertTrue(row.get("limitedNames") instanceof Collection);
        }
    }

    public static class CustomFunction extends BaseQueryFunctionLayer implements QueryFunctionLayerInterface {

        public CustomFunction() {
            super("customFunction");
        }

        @Override
        public Object evaluate(String functionName, Object... parameters) {
            return ((Integer)parameters[0]) % 2 == 0 ? "P" : "I";
        }
    }

    public static class CharacterResource extends Layer implements ReadRowsLayerInterface {

        @Override
        public String getImplName() {
            return "character";
        }

        @Override
        public Collection<JoinableMap> readRows(Queryable queryable) {
            return queryable.evaluate(simpsonCharacters.values());
        }
    }

    public static class Character2Resource extends Layer implements ReadRowsLayerInterface {

        @Override
        public String getImplName() {
            return "character2";
        }

        @Override
        public Collection<JoinableMap> readRows(Queryable queryable) {
            return queryable.evaluate(simpsonCharacters2.values());
        }
    }

    public static class AddressResource extends Layer implements ReadRowsLayerInterface {

        @Override
        public String getImplName() {
            return "address";
        }

        @Override
        public Collection<JoinableMap> readRows(Queryable queryable) {
            return queryable.evaluate(simpsonAddresses.values());
        }
    }

}
