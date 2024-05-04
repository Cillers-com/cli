require 'fileutils'

module Commands
  module New
    def self.execute(system_name, verbose)
      check_git_installed(verbose)
      check_directory_exists(system_name, verbose)

      url = "https://github.com/Cillers-com/create-cillers-system"
      puts "Creating new system named '#{system_name}'..."
      execute_command("git clone #{url} #{system_name}", verbose)

      Dir.chdir(system_name) do
        FileUtils.rm_rf('.git')
        puts "Removed original Git repository." if verbose

        puts "Initializing new Git repository..."
        execute_command("git init", verbose)
        execute_command("git add .", verbose)
        execute_command("git commit -m 'Initial commit'", verbose)
      end

      puts "New Cillers system '#{system_name}' successfully created."
    end

    private

    def self.check_git_installed(verbose)
      if `git --version`.empty?
        puts "Error: Git is not installed or not in the PATH." if verbose
        exit 1
      end
    end

    def self.check_directory_exists(system_name, verbose)
      if Dir.exist?(system_name)
        puts "Error: Directory '#{system_name}' already exists." if verbose
        exit 1
      end
    end

    def self.execute_command(command, verbose)
      if verbose
        puts "Executing: #{command}"
        system(command)
      else
        system("#{command} > /dev/null 2>&1")
      end
    end
  end
end

