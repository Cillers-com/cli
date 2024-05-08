module Commands
  def self.help

    puts <<-EOS
Usage: cillers [command] [options]

Commands:
  new <name>        Create a new system with the specified name.
  help              Show this help message.
  version           Show the version number.

Options:
  --verbose        Enable verbose output for debugging purposes.
EOS

  end
end

