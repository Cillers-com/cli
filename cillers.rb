#!/usr/bin/env ruby

$verbose = ARGV.include?("--verbose")
ARGV.delete("--verbose")

require 'pathname'

# Find the actual libexec directory
libexec = Pathname.new(File.realpath(__FILE__)).dirname.parent + 'libexec'

# Require files from libexec
$LOAD_PATH.unshift(libexec.to_s)  # Add libexec to the load path

require 'src/commands/new'
require 'src/commands/help'

command = ARGV.shift

case command
when "new"
  system_name = ARGV.shift
  unless system_name
    puts "Error: No system name provided." if $verbose
    exit 1
  end
  Commands::New.execute(system_name, $verbose)
when "help", nil  # Include 'nil' to show help when no command is provided
  Commands::Help.display
else
  puts "Error: Unknown command '#{command}'" if $verbose
  Commands::Help.display
end

