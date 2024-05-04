module Commands
  module Help
    def self.display
      puts <<-EOS
Usage: cillers [command] [options]

Commands:
  new <name>       Create a new system with the specified name.
  help             Show this help message.

Options:
  --verbose        Enable verbose output for debugging purposes.
EOS
    end
  end
end

