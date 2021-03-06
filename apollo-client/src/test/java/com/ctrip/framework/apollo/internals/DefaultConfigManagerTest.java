package com.ctrip.framework.apollo.internals;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spi.ConfigFactory;
import com.ctrip.framework.apollo.spi.ConfigFactoryManager;

import org.junit.Before;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigManagerTest extends ComponentTestCase {
  private DefaultConfigManager defaultConfigManager;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    defineComponent(ConfigFactoryManager.class, MockConfigManager.class);
    defaultConfigManager = (DefaultConfigManager) lookup(ConfigManager.class);
  }

  @Test
  public void testGetConfig() throws Exception {
    String someNamespace = "someName";
    String anotherNamespace = "anotherName";
    String someKey = "someKey";
    Config config = defaultConfigManager.getConfig(someNamespace);
    Config anotherConfig = defaultConfigManager.getConfig(anotherNamespace);

    assertEquals(someNamespace + ":" + someKey, config.getProperty(someKey, null));
    assertEquals(anotherNamespace + ":" + someKey, anotherConfig.getProperty(someKey, null));
  }

  @Test
  public void testGetConfigMultipleTimesWithSameNamespace() throws Exception {
    String someNamespace = "someName";
    Config config = defaultConfigManager.getConfig(someNamespace);
    Config anotherConfig = defaultConfigManager.getConfig(someNamespace);

    assertThat(
        "Get config multiple times with the same namespace should return the same config instance",
        config, equalTo(anotherConfig));
  }

  public static class MockConfigManager implements ConfigFactoryManager {

    @Override
    public ConfigFactory getFactory(String namespace) {
      return new ConfigFactory() {
        @Override
        public Config create(final String namespace) {
          return new AbstractConfig() {
            @Override
            public String getProperty(String key, String defaultValue) {
              return namespace + ":" + key;
            }
          };
        }
      };
    }
  }
}
