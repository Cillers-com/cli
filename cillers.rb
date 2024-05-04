#!/usr/bin/env ruby
require 'fileutils'

# Function to execute system commands
def execute_command(command)
  puts "Executing: #{command}"
  system(command)
end

# Function to check if Git is installed
def check_git_installed
  if `git --version`.empty?
    puts "Error: Git is not installed or not in the PATH."
    exit 1
  end
end

# Function to check if the target directory already exists
def check_directory_exists(system_name)
  if Dir.exist?(system_name)
    puts "Error: Directory '#{system_name}' already exists."
    exit 1
  end
end

command = ARGV.shift

case command
when "new"
  system_name = ARGV.shift  # Get the system name from the command line

  unless system_name
    puts "Error: No system name provided."
    exit 1
  end

  # Pre-operation checks
  check_git_installed
  check_directory_exists(system_name)

  # Step 1: Clone the repository
  url = "https://github.com/Cillers-com/create-cillers-system"
  execute_command("git clone #{url} #{system_name}")

  # Navigate to the newly created directory
  Dir.chdir(system_name) do
    # Step 2: Remove the .git directory to disconnect from the source repository
    FileUtils.rm_rf('.git')

    # Step 3: Initialize a new Git repository
    execute_command("git init")
    execute_command("git add .")
    execute_command("git commit -m 'Initial commit'")
  end

  puts "#{system_name} created and initialized successfully."

else
  puts "Error: Unknown command '#{command}'"
end
