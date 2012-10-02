package com.karthik.wext.site.wizair;

import com.karthik.wext.configs.SiteName;
import com.karthik.wext.core.AbstractSiteStrategy;

import java.io.IOException;

/**
 * Created by volodymyrd on 11.09.14.
 */
public class WizairSite extends AbstractSiteStrategy{

    @Override
    protected void step2_CollectGenre() throws IOException {

    }

    @Override
    protected void step3_CollectLinks() throws IOException {

    }

    @Override
    protected void step5_SaveInfoToXls() throws WriteException, IOException {

    }

    @Override
    public SiteName getSiteName() {
        return null;
    }
}
