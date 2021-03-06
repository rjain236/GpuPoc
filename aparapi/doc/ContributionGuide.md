#ContributionGuide
*How to contribute (bug fix or features). Updated Sep 13, 2011 by frost.g...@gmail.com*
##Contribution Guide
We welcome all contributions to add new features to Aparapi and make Aparapi more useful and high performing. These guidelines are intended to describe and streamline the contribution process.

A patch can be a bug fix, a new feature, a new JUnit test case or a documentation change.

Only members of the commit team are able to commit changes to the SVN repository.

Only patches submitted through the process described below will be committed to SVN.

The commit team will only applying patches which are submitted via the Aparapi project’s issue list.

http://code.google.com/p/aparapi/issues/list
The current commit team members are:
* Eric Caspole (AMD)
* Tom Deneau (AMD)
* Gary Frost (AMD)

If you would like to be considered for inclusion in the commit team, please send an email to anyone on the team and let them know.

##Submitting a patch
If the bug or enhancement does not yet appear in the issues list, please take the time add a new issue.

Be sure to include sufficient detail to explain and recreate the bug or to justify the proposed enhancement.

Ensure that your patch/fix does not regress any of existing JUnit tests. The UnitTestGuide wiki page describes how to run the various Aparapi unit tests.

Ensure that your patch does not break any sample or example. Create a patch file (using SVN’s diff command) against a recently updated trunk, do not submit patches against branches. Name your patch file using the following filename convention

     aparapi-<issue id>-<trunk revision id>.patch
The following shows the sequence for creating a patch for issue number 1234.

    $ cd aparapi-trunk
    $ svn update
    At revision 10339
    $ svn -diff > aparapi-1234-10339.patch

Attach your patch file to the issue via Issue List.

## Attribution of contributions
We want to correctly attribute all contributions and will maintain a CREDITS.txt file at the head of the trunk. We discourage including attribution as comments in the code, instead we intend to let the history feature of SVN be the primary method for tracking attributions. When patch is committed the commit team member will update the CREDITS.txt file and apply your patch, then will include your name (and email if you desire) as part of the SVN commit history.

## Contributions made under a different license than the existing BSD derived license
We cannot accept contributions or patches which are subject to other licenses.

Attribution

