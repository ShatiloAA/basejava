package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AbstractArrayStorage.class,
        ListStorageTest.class,
        MapStorageTest.class,
        MapStorageResumeSearchKeyTest.class
})

public class AllTestRunner {
}
