module ARGV_Parser

  SUPPORTED_OPTIONS = [
    :verbose
  ]

  SUPPORTED_COMMANDS = [
    :new, 
    :version, 
    :help
  ]

  def self.split_argv(argv)
    # Support --help and --version options because it is expected, but treat them like commands, 
    # because their semantics is better aligned with being commands. 
    return [['help'], []] if argv.include? "--help"
    return [['version'], []] if argv.include? "--version"

    first_option_index = argv.find_index { |arg| arg.start_with?('-') } || argv.length
    command_argv = argv.take(first_option_index)
    option_argv = argv.drop(first_option_index)
    
    raise ArgumentError, "No command provided" unless command_argv.first
    command = command_argv.first.to_sym
    raise ArgumentError, "Unsported command: #{command}" unless SUPPORTED_COMMANDS.include? command

    args = command_argv[1..] 

    options = options_from_option_argv(option_argv)

    [command, args, options]
  end

  private

  def self.options_from_option_argv(option_argv)
    options = {}

    i = 0
    while i < option_argv.length
      arg = option_argv[i]
      option = arg.sub(/^--/, '').to_sym
      
      unless SUPPORTED_OPTIONS.include?(option)
        raise ArgumentError, "Unsupported option: #{arg}"
      end
      
      options[option] = true

      i += 1
    end
  end
end
