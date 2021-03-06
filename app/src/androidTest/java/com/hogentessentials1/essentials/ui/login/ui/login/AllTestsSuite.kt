package com.hogentessentials1.essentials.ui.login.ui.login

import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * @author Jonathan Vanden Eynden
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AuthenticationTest::class,
    ChangeInitiativeTest::class,
    HomescreenTest::class,
    LoginActivityTest::class,
    RoadmapItemTest::class,
    MyTeamsTest::class
)
class AllTestsSuite
