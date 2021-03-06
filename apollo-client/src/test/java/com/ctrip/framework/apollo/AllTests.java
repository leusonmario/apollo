package com.ctrip.framework.apollo;


import com.ctrip.framework.apollo.integration.ConfigIntegrationTest;
import com.ctrip.framework.apollo.internals.DefaultConfigManagerTest;
import com.ctrip.framework.apollo.internals.DefaultConfigTest;
import com.ctrip.framework.apollo.internals.LocalFileConfigRepositoryTest;
import com.ctrip.framework.apollo.internals.RemoteConfigRepositoryTest;
import com.ctrip.framework.apollo.internals.SimpleConfigTest;
import com.ctrip.framework.apollo.spi.DefaultConfigFactoryManagerTest;
import com.ctrip.framework.apollo.spi.DefaultConfigFactoryTest;
import com.ctrip.framework.apollo.spi.DefaultConfigRegistryTest;
import com.ctrip.framework.apollo.util.ExceptionUtilTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ConfigServiceTest.class, DefaultConfigRegistryTest.class, DefaultConfigFactoryManagerTest.class,
    DefaultConfigManagerTest.class, DefaultConfigTest.class, LocalFileConfigRepositoryTest.class,
    RemoteConfigRepositoryTest.class, SimpleConfigTest.class, DefaultConfigFactoryTest.class,
    ConfigIntegrationTest.class, ExceptionUtilTest.class
})
public class AllTests {

}
