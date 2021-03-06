/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.datastores.memcached.app;

import java.io.IOException;

import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.datastores.memcached.IMemcachedSettings;
import org.wicketstuff.datastores.memcached.MemcachedDataStore;
import org.wicketstuff.datastores.memcached.MemcachedSettings;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication
{
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();

		mountPage("page1", HomePage.class);
		mountPage("page2", Page2.class);

		// for testing IDataStore#getData().
		getStoreSettings().setInmemoryCacheSize(1);

		setPageManagerProvider(new DefaultPageManagerProvider(this)
		{
			@Override
			protected IDataStore newDataStore()
			{
				IMemcachedSettings settings = new MemcachedSettings();
				try
				{
					return new MemcachedDataStore(settings);
				} catch (IOException iox)
				{
					throw new RuntimeException(iox);
				}
			}
		});
	}
}
