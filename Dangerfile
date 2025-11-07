####
#
# github comment settings
#
####
github.dismiss_out_of_range_messages

####
#
# Android Lint
#
####
android_lint.gradle_task = "app:lintDebug"
workspace = ENV['GITHUB_WORKSPACE'] || Dir.pwd
android_lint.report_file = File.join(workspace, "app/build/reports/lint-results-debug.xml")
android_lint.filtering = true
if File.exist?(android_lint.report_file)
  android_lint.lint(inline_mode: true)
else
  warn("Lint report not found at #{android_lint.report_file}")
end
