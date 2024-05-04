#!/usr/bin/env ruby

$verbose = ARGV.include?("--verbose")
ARGV.delete("--verbose")

require_relative 'src/commands/new'
require_relative 'src/commands/help'

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

