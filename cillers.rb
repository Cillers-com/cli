#!/usr/bin/env ruby

require 'pathname'

require_relative './src/lib/argv_parser'
require_relative './src/lib/execution'

begin
  command, args, options = ARGV_Parser::split_argv(ARGV)
  Execution::dispatch_command(command, args, option)
rescue ArgumentError => e
  puts "Invalid arguments provided: #{e.message}" 
rescue RuntimeError => e
  puts "RuntimeError: #{e.message}"
rescue
  puts e.message
end
