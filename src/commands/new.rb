require 'fileutils'

module Commands
  def self.new(args, options)
    raise ArgumentError, "No name provided" if args.length < 1
    raise ArgumentError, "Command 'new' takes only one argument" if args.length > 1
    raise ArgumentError, "Directory #{name} already exists" if Dir.exist?(name)
    raise RuntimeError, "Git is not installed or not in the PATH." if `git --version`.empty?

    name = args.first
    verbose = options[:verbose]

    template_url = "https://github.com/Cillers-com/create-cillers-system"
    puts "Creating new system named '#{name}'..."
    execute_command("git clone #{template_url} #{name}", verbose)

    raise Error, "The directory was not created successfully" unless Dir.exists?(name) 

    Dir.chdir(system_name) do
      FileUtils.rm_rf('.git')
      puts "Removed original Git repository." if verbose

      puts "Initializing new Git repository..."
      execute_command("git init", verbose)
      execute_command("git add .", verbose)
      execute_command("git commit -m 'Initial commit'", verbose)
    end

    puts "New Cillers system '#{name}' successfully created."
  end

  private

  def self.execute_command(command, verbose)
    if verbose
      puts "Executing: #{command}"
      system(command)
    else
      system("#{command} > /dev/null 2>&1")
    end
  end
end

