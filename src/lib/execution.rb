require_relative '../commands/new'
require_relative '../commands/help'
require_relative '../commands/version'

module Execution
  def dispatch_command(command, args, options)
    unless Commands.respond_to?(command)
      raise ArgumentError, "Unsupported command: #{command}"
    end

    Commands.public_send(command, args, options)
  end
end
