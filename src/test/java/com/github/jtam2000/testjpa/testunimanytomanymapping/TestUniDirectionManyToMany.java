package com.github.jtam2000.testjpa.testunimanytomanymapping;

import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.dao.JPARegistry;
import com.github.jtam2000.jpa.relationships.manytomany.MyStampCollection;
import com.github.jtam2000.jpa.relationships.manytomany.Stamp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class TestUniDirectionManyToMany {

    private JPADataAccessDaoImpl<Stamp> dao;
    private final Class<Stamp> targetClass = Stamp.class;
    private JPADataAccessDaoImpl<MyStampCollection> collectionDao;
    private final Class<MyStampCollection> collectionClass = MyStampCollection.class;
    private boolean tearDown = true;
    private JPA jpa;

    @Before
    public void runBeforeEachTest() {

        createDaos();
        deleteExistingData();

    }

    @After
    public void runAfterEachTest() {

        if (tearDown)
            dao.delete();
        dao.close();
    }

    private void deleteExistingData() {

        deleteExistingDataInOrder();
    }

    private void deleteExistingDataInOrder() {

        deleteRootThenDependencies(collectionDao);
    }

    private void deleteRootThenDependencies(JPADataAccessDaoImpl<MyStampCollection> dependencies) {

        dao.delete();
        dependencies.delete();
    }

    private JPA createJPU() {

        String JPUString = "jpu_relationship_many_to_many";
        return new JPA(JPUString);
    }

    private void createDaos() {

        jpa = createJPU();
        createDaos(jpa);
    }

    private void createDaos(JPA jpu) {

        dao = new JPADataAccessDaoImpl<>(jpu, targetClass);
        collectionDao = new JPADataAccessDaoImpl<>(jpu, collectionClass);
    }

    private void doNotTearDown() {

        tearDown = false;
    }

    @Test
    //CRud
    public void test_createOneStampToManyCollection() {

        //given @Before:
        Stamp stamp = Stamp.randomDefinitiveStamp("HONG_KONG");

        MyStampCollection italianCollection = MyStampCollection.randomCollection("Italian Collection");
        MyStampCollection chinaCollectionI = MyStampCollection.randomCollection("China Collection I");
        MyStampCollection chinaCollectionII = MyStampCollection.randomCollection("China Collection II");
        stamp.add(italianCollection);
        stamp.add(chinaCollectionI);
        stamp.add(chinaCollectionII);

        dao.create(List.of(stamp));

        Stamp found = dao.findByPrimaryKey(stamp);
        System.out.println("found stamp:" + found);

        assertEquals("created/persisted stamp should be same as queried:", stamp, found);

        doNotTearDown();
    }


    @Test
    //CRud
    public void test_createTwoStampToManyCollection() {

        //given @Before:
        Stamp hkStamp = Stamp.randomDefinitiveStamp("HONG_KONG");
        Stamp italianStamp = Stamp.randomDefinitiveStamp("Italy");

        MyStampCollection italianCollection = MyStampCollection.randomCollection("Italian Collection");
        MyStampCollection chinaCollectionI = MyStampCollection.randomCollection("China Collection I");
        MyStampCollection chinaCollectionII = MyStampCollection.randomCollection("China Collection II");

        hkStamp.add(chinaCollectionI);
        hkStamp.add(chinaCollectionII);

        italianStamp.add(italianCollection);

        dao.create(List.of(hkStamp, italianStamp));

        Stamp found = dao.findByPrimaryKey(hkStamp);
        assertEquals("created/persisted stamp should be same as queried:", hkStamp, found);
        assertEquals("hong kong stamp is in the china stamp collection I and II",
                Set.of(chinaCollectionI, chinaCollectionII), found.getCollections());

        found = dao.findByPrimaryKey(italianStamp);
        assertEquals("created/persisted stamp should be same as queried:", italianStamp, found);
        assertEquals("Italian stamp is in the Italian collection",
                Set.of(italianCollection), found.getCollections());

        doNotTearDown();
    }


    @Test
    //crUd
    public void test_changeOneStampToADifferentCollection() {

        //given + @Before
        List<MyStampCollection> collections = registerCollections(
                MyStampCollection.randomCollection("China Collection I"),
                MyStampCollection.randomCollection("China Collection II"));

        Stamp hkStamp = persistStampToTheCollectionI(collections);

        //when
        moveStampFromCollectionOneToTwo(collections, hkStamp);

        //then
        Stamp found = dao.findByPrimaryKey(hkStamp);
        assertEquals("created/persisted stamp should be same as queried:", hkStamp, found);
        assertEquals("hong kong stamp should only be in China Collection II",
                Set.of(collections.get(1)), found.getCollections());

    }

    private void moveStampFromCollectionOneToTwo(List<MyStampCollection> collections, Stamp hkStamp) {

        hkStamp.getCollections().remove(collections.get(0));
        hkStamp.add(collections.get(1));
        dao.update(List.of(hkStamp));
    }

    private Stamp persistStampToTheCollectionI(List<MyStampCollection> collections) {

        Stamp hkStamp = Stamp.randomDefinitiveStamp("HONG_KONG");
        hkStamp.add(collections.get(0));
        dao.create(List.of(hkStamp));
        return hkStamp;
    }

    private  List<MyStampCollection> registerCollections(MyStampCollection chinaCollectionI,
                                                        MyStampCollection chinaCollectionII) {

        List<MyStampCollection> collections = List.of(chinaCollectionI, chinaCollectionII);
        JPARegistry<MyStampCollection> collReg = new JPARegistry<>(jpa, MyStampCollection.class);
        collReg.findOrCreate(collections);
        return collections;
    }

}
