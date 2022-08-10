(defproject me.raynes/fs "1.4.7"
  :description "File system utilities for clojure"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :url "https://github.com/FarmLogs/fs"
  :min-lein-version "2.0.0"
  :plugins [[lein-file-replace "0.1.0"]
            [s3-wagon-private "1.3.5"]
            [lein-midje "3.1.3"]
            [codox "0.8.10"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}}
  :repositories {"releases" {:url "s3p://fl-maven-repo/mvn"
                             :username :env/AMAZON_KEY
                             :passphrase :env/AMAZON_SECRET
                             :sign-releases false}}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.apache.commons/commons-compress "1.21"]
                 [org.tukaani/xz "1.9"]]
  :test-selectors {:default (complement :integration)
                   :integration :integration
                   :third-party :third-party
                   :docker (complement :third-party)
                   :all (constantly true)}

  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version"
                   "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "--no-sign"]
                  ["deploy"]
                  ["file-replace" "README.md" "\\[api \"" "\"]" "version"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]])
